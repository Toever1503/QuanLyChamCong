package com.web;


import com.Util.RequestStatusUtil;
import com.dto.DayOffDTO;
import com.dto.ResponseDto;
import com.model.DayOffModel;
import com.service.DayOffService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/day-offs")
public class DayOffResources {

    private final DayOffService dayOffService;

    public DayOffResources(DayOffService dayOffService) {
        this.dayOffService = dayOffService;
    }

    @GetMapping
    public ResponseDto gettAll(Pageable page) {
        return ResponseDto.of(this.dayOffService.findAll(page).map(DayOffDTO::toDto), "Get all dayoff");
    }

    @GetMapping("get-request-by-manager/{id}")
    public ResponseDto getRequestByManager(@PathVariable Long id, Pageable page) {
        return ResponseDto.of(this.dayOffService.findAllRequestForManager(id, page).map(DayOffDTO::toDto), "Get all request dayoff of manager: " + id);
    }

    @PostMapping
    public ResponseDto add(@RequestBody DayOffModel model) {
        model.setId(null);
        return ResponseDto.of(DayOffDTO.toDto(this.dayOffService.add(model)), "Add dayoff");
    }

    @PatchMapping("{id}")
    public ResponseDto update(@PathVariable Long id, @RequestBody DayOffModel model, @RequestParam RequestStatusUtil status) {
        model.setId(id);
        model.setStatus(status.name());
        return ResponseDto.of(DayOffDTO.toDto(this.dayOffService.update(model)), "update dayoff");
    }

    @DeleteMapping("{id}")
    public ResponseDto deleteById(@PathVariable Long id) {
        return ResponseDto.of(this.dayOffService.deleteById(id) == false ? null : true, "Delete dayoff with id: " + id);
    }

    @GetMapping("change-status/{id}")
    public ResponseDto changeStatus(@PathVariable Long id, @RequestParam RequestStatusUtil status) {
        return ResponseDto.of(DayOffDTO.toDto(this.dayOffService.changeStatus(id, status)), "Change status dayoff with id: " + id);
    }
}
