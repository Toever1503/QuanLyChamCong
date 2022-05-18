package com.controller;

import com.dto.ResponseDto;
import com.dto.TimeKeepingDto;
import com.entity.TimeKeeping;
import com.model.RequestUtil;
import com.model.TimeKeepingModel;
import com.service.impl.TimekeepingServiceimpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/timekeeping")
public class TimekeepingController {
    private final TimekeepingServiceimpl timekeepingService;

    public TimekeepingController(TimekeepingServiceimpl timekeepingService) {
        this.timekeepingService = timekeepingService;
    }

    @Transactional
    @GetMapping
    public Object getAllTimeKeepingsPage(Pageable page) {
        Page<TimeKeepingDto> timeKeepingDtos = timekeepingService.findAll(page).map(TimeKeepingDto::entityToDto);
        return ResponseDto.of(timeKeepingDtos, "Get all TimeKeepings Page");
    }


//    @GetMapping()
//    public Object getAllTimeKeepings(){
//        List<TimeKeepingDto> timekeepingDtos = timekeepingService.findAll().stream().map(TimeKeepingDto::entityToDto).collect(Collectors.toList());
//        return ResponseDto.of(timekeepingDtos, "Get all Timekeepings");
//    }

    @GetMapping("/{id}")
    public Object getTimekeepingById(@PathVariable("id") Long id) {
        return ResponseDto.of(timekeepingService.findById(id), "Get TimeKeeping by id");
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
    @PatchMapping("/{id}")
    public Object updateTimeKeeping(@PathVariable("id") Long id, @RequestParam("status") String status) {
        TimeKeeping timeKeeping = timekeepingService.findById(id);
        TimeKeepingModel setTimekeepingStatus = new TimeKeepingModel();

        if (status.equals("approved")) {
            setTimekeepingStatus.setStatus(RequestUtil.APPROVED);
        }else if (status.equals("rejected")) {
            setTimekeepingStatus.setStatus(RequestUtil.REJECTED);
        }
        TimeKeepingModel timeKeepingModel = TimeKeepingModel.builder()
                .id(id)
                .timeIn(timeKeeping.getTimeIn())
                .timeOut(timeKeeping.getTimeOut())
                .status(setTimekeepingStatus.getStatus())
                .build();

        TimeKeeping timeKeepingResult =  timekeepingService.update(timeKeepingModel);
        TimeKeepingDto timeKeepingDto = TimeKeepingDto.entityToDto(timeKeepingResult);
        return ResponseDto.of(timeKeepingDto, "Update timekeeping success");
    }

    @Transactional
    @DeleteMapping("/{id}")
    public Object deleteTimeKeeping(@PathVariable("id") Long id, Pageable pageable) {
        if (timekeepingService.deleteById(id)) {
            return ResponseDto.of(timekeepingService.findAll(pageable).map(TimeKeepingDto::entityToDto), "Delete timekeeping success");
        }else {
            return ResponseDto.of(null, "Delete timekeeping fail");
        }

    }
}
