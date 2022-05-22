package com.web;

import com.Util.RequestStatusUtil;
import com.dto.ResponseDto;
import com.dto.TimeKeepingDto;
import com.dto.TimeLateDto;
import com.entity.TimeLate;
import com.model.TimelateModel;
import com.service.ITimeLateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/timeLate")
public class TimeLateResources {
    private final ITimeLateService timeLateService;

    public TimeLateResources(ITimeLateService timeLateService) {
        this.timeLateService = timeLateService;
    }

    @Transactional
    @GetMapping
    public Object getAllTimeLatePage(Pageable page) {
        Page<TimeLateDto> timeLateDtos = timeLateService.findAll(page).map(TimeLateDto::entityToDto);
        return ResponseDto.of(timeLateDtos, "Get all TimeKeepings Page");
    }
    @Transactional
    @GetMapping("my-request")
    public ResponseDto getAllRequests( Pageable page) {
        return ResponseDto.of(this.timeLateService.findAllMyRequests(page).map(TimeLateDto::entityToDto), "Get all request time late by staff");
    }

    @Transactional
    @GetMapping("/{id}")
    public Object getTimeLateById(@PathVariable("id") Long id) {
        return ResponseDto.of(timeLateService.findById(id), "Get TimeKeeping by id");
    }

    // staff send request timeLate
    @Transactional
    @PostMapping
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public Object addTimeLate(@RequestBody TimelateModel timelateModel) {
        TimeLate timeLate = timeLateService.add(timelateModel);
        TimeLateDto timeLateDto = TimeLateDto.entityToDto(timeLate);
        return ResponseDto.of(timeLateDto, "Add request time late success");
    }

    // management approve request timeLater
    @Transactional

    @PatchMapping("change-status")
    public Object changeStatus(@RequestBody List<Long> ids, @RequestParam("status") RequestStatusUtil status) {
        return ResponseDto.of(timeLateService.changeStatus(ids, status) == true ? true : null, "Update request time late success");
    }

    @Transactional
    @DeleteMapping("/{id}")
    public Object deleteTimeKeeping(@PathVariable("id") Long id, Pageable pageable) {
        if (timeLateService.deleteById(id)) {
            return ResponseDto.of(timeLateService.findAll(pageable).map(TimeLateDto::entityToDto), "Delete request time late success");
        } else {
            return ResponseDto.of(null, "Delete timekeeping fail");
        }
    }

    @Transactional
    @GetMapping("get-request-by-date/{date}")
    public ResponseDto getAllRequestsByDate(@PathVariable long date, Pageable page) {
        return ResponseDto.of(this.timeLateService.getAllRequestsByDate(date, page).map(TimeLateDto::entityToDto), "Get all request time late by date: " + date);
    }

}
