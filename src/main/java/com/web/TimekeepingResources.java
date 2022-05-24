package com.web;

import com.Util.SecurityUtil;
import com.dto.ResponseDto;
import com.dto.TimeKeepingDto;
import com.entity.Position;
import com.entity.TimeKeeping;
import com.Util.RequestStatusUtil;
import com.model.TimeKeepingModel;
import com.service.ITimeKeepingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/timekeeping")
public class TimekeepingResources {
    private final ITimeKeepingService timekeepingService;

    public TimekeepingResources(ITimeKeepingService timekeepingService) {
        this.timekeepingService = timekeepingService;
    }

    @Transactional
    @GetMapping
    public Object getAllTimeKeepingsPage(Pageable page) {
        Page<TimeKeepingDto> timeKeepingDtos = timekeepingService.findAll(page).map(TimeKeepingDto::entityToDto);
        return ResponseDto.of(timeKeepingDtos, "Get all TimeKeepings Page");
    }

    @RolesAllowed(Position.ADMINISTRATOR)
    @Transactional
    @GetMapping("all-request/{staffId}")
    public ResponseDto getAllStaffRequests(@PathVariable long staffId, Pageable page) {
        return ResponseDto.of(timekeepingService.findAllStaffRequests(staffId, page).map(TimeKeepingDto::entityToDto), "Get all staff Timekeepings");
    }

    @Transactional
    @GetMapping("my-request")
    public Object getAllRequests(Pageable page) {
        return ResponseDto.of(timekeepingService.findAllStaffRequests(SecurityUtil.getCurrentUserId(), page).map(TimeKeepingDto::entityToDto), "Get all my Timekeepings");
    }

    @Transactional
    @GetMapping("/{id}")
    public Object getTimekeepingById(@PathVariable("id") Long id) {
        return ResponseDto.of(TimeKeepingDto.entityToDto(timekeepingService.findById(id)), "Get TimeKeeping by id");
    }

    // staff send request timeKeeping
    @Transactional
    @PostMapping
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public Object addTimekeeping(@RequestBody TimeKeepingModel timeKeepingModel) {
        TimeKeeping timeKeeping = timekeepingService.add(timeKeepingModel);
        TimeKeepingDto timeKeepingDto = TimeKeepingDto.entityToDto(timeKeeping);
        return ResponseDto.of(timeKeepingDto, "Add timeKeeping success");
    }

    // management approve request timeKeeping
    @Transactional

    @PatchMapping("change-status")
    public Object changeStatus(@RequestBody List<Long> ids, @RequestParam("status") RequestStatusUtil status) {
        return ResponseDto.of(timekeepingService.changeStatus(ids, status) == true ? true : null, "Update timekeeping success");
    }

    @Transactional
    @DeleteMapping("/{id}")
    public Object deleteTimeKeeping(@PathVariable("id") Long id, Pageable pageable) {
        if (timekeepingService.deleteById(id)) {
            return ResponseDto.of(timekeepingService.findAll(pageable).map(TimeKeepingDto::entityToDto), "Delete timekeeping success");
        } else {
            return ResponseDto.of(null, "Delete timekeeping fail");
        }
    }

    @Transactional
    @GetMapping("get-request-by-date/{timein}/{timeout}")
    public ResponseDto getAllRequestsByDate(@PathVariable long timein,@PathVariable long timeout, Pageable page) {
        return ResponseDto.of(this.timekeepingService.getAllRequestsByTime(timein, timeout, page).map(TimeKeepingDto::entityToDto), "Get all request timekeeping by date: " + timein + timeout);
    }

}
