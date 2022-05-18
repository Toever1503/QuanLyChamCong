package com.web;

import com.dto.ResponseDto;
import com.dto.TimeKeepingDto;
import com.entity.TimeKeeping;
import com.Util.RequestStatusUtil;
import com.model.TimeKeepingModel;
import com.service.ITimeKeepingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

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
    public Object updateTimeKeeping(@PathVariable("id") Long id, @RequestParam("status") RequestStatusUtil status) {
        return ResponseDto.of(TimeKeepingDto.entityToDto(timekeepingService.changeStatus(id, status)), "Update timekeeping success");
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

}
