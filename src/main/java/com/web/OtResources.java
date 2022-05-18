package com.web;

import com.Util.RequestStatusUtil;
import com.dto.OTDto;
import com.dto.ResponseDto;
import com.entity.OtModel;
import com.service.OTService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/ots")
@RestController
public class OtResources {
    private final OTService otService;

    public OtResources(OTService otService) {
        this.otService = otService;
    }

    @GetMapping
    public ResponseDto gettAll(Pageable page) {
        return ResponseDto.of(this.otService.findAll(page).map(OTDto::toDto), "Get all ots");
    }

    @GetMapping("get-request-by-manager/{id}")
    public ResponseDto getRequestByManager(@PathVariable Long id, Pageable page) {
        return ResponseDto.of(this.otService.findAllRequestForManager(id, page).map(OTDto::toDto), "Get all ots of manager: " + id);
    }

    @PostMapping
    public ResponseDto add(@RequestBody OtModel model) {
        model.setId(null);
        return ResponseDto.of(OTDto.toDto(this.otService.add(model)), "Add ot");
    }

    @PatchMapping("{id}")
    public ResponseDto update(@PathVariable Long id, @RequestBody OtModel model) {
        model.setId(id);
        return ResponseDto.of(OTDto.toDto(this.otService.update(model)), "Add ot");
    }

    @DeleteMapping("{id}")
    public ResponseDto deleteById(@PathVariable Long id) {
        return ResponseDto.of(this.otService.deleteById(id) == false ? null : true, "Delete staff with id: " + id);
    }

    @GetMapping("change-status/{id}")
    public ResponseDto changeStatus(@PathVariable Long id, @RequestParam RequestStatusUtil status) {
        return ResponseDto.of(OTDto.toDto(this.otService.changeStatus(id, status)), "Change status ot with id: " + id);
    }


}
