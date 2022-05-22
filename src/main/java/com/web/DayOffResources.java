package com.web;


import com.Util.RequestStatusUtil;
import com.dto.DayOffDTO;
import com.dto.ResponseDto;
import com.model.DayOffModel;
import com.service.DayOffService;
import io.swagger.annotations.Authorization;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("api/day-offs")
public class DayOffResources {

    private final DayOffService dayOffService;

    public DayOffResources(DayOffService dayOffService) {
        this.dayOffService = dayOffService;
    }

    @Transactional
    @GetMapping
    public ResponseDto gettAll(Pageable page) {
        return ResponseDto.of(this.dayOffService.findAll(page).map(DayOffDTO::toDto), "Get all request dayoff");
    }

    @Transactional
    @GetMapping("{id}")
    public ResponseDto getById(@PathVariable Long id) {
        return ResponseDto.of(DayOffDTO.toDto(this.dayOffService.findById(id)), "Get request dayoff by id: " + id);
    }

    @Transactional
    @GetMapping("my-request")
    public ResponseDto getAllMyRequest(Pageable page) {
        return ResponseDto.of(this.dayOffService.findAllMyRequests(page).map(DayOffDTO::toDto), "Get all my request dayoff: ");
    }


    @Transactional
    @PostMapping
    public ResponseDto add(@RequestBody DayOffModel model) {
        model.setId(null);
        return ResponseDto.of(DayOffDTO.toDto(this.dayOffService.add(model)), "Add request dayoff");
    }

    @Transactional
    @PutMapping("{id}")
    public ResponseDto update(@PathVariable Long id, @RequestBody DayOffModel model, @RequestParam RequestStatusUtil status) {
        model.setId(id);
        model.setStatus(status.name());
        return ResponseDto.of(DayOffDTO.toDto(this.dayOffService.update(model)), "update request dayoff");
    }

    @Transactional
    @DeleteMapping("{id}")
    public ResponseDto deleteById(@PathVariable Long id) {
        return ResponseDto.of(this.dayOffService.deleteById(id) == false ? null : true, "Delete request dayoff with id: " + id);
    }

    @Transactional
    @PatchMapping("change-status")
    public ResponseDto changeStatus(@RequestBody List<Long> ids, @RequestParam("status") RequestStatusUtil status) {
        return ResponseDto.of(this.dayOffService.changeStatus(ids, status) == true ? true : null, "Update request dayoff success");
    }

    @Transactional
    @GetMapping("get-request-by-date/{date}")
    public ResponseDto getAllRequestsByDate(@PathVariable long date, Pageable page) {
        return ResponseDto.of(this.dayOffService.getAllRequestsByDate(date, page).map(DayOffDTO::toDto), "Get all request dayoff by date: " + date);
    }
}
