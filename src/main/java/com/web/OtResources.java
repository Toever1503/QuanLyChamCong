package com.web;

import com.Util.RequestStatusUtil;
import com.dto.OTDto;
import com.dto.ResponseDto;
import com.entity.OtModel;
import com.service.OTService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RequestMapping("api/ots")
@RestController
public class OtResources {
    private final OTService otService;

    public OtResources(OTService otService) {
        this.otService = otService;
    }

    @Transactional
    @GetMapping
    public ResponseDto gettAll(Pageable page) {
        return ResponseDto.of(this.otService.findAll(page).map(OTDto::toDto), "Get all ots");
    }

    @Transactional
    @GetMapping("get-request-by-manager/{id}")
    public ResponseDto getRequestByManager(@PathVariable Long id, Pageable page) {
        return ResponseDto.of(this.otService.findAllRequestForManager(id, page).map(OTDto::toDto), "Get all ots of manager: " + id);
    }

    @Transactional
    @PostMapping
    public ResponseDto add(@RequestBody OtModel model) {
        model.setId(null);
        return ResponseDto.of(OTDto.toDto(this.otService.add(model)), "Add ot");
    }

    @Transactional
    @PutMapping("{id}")
    public ResponseDto update(@PathVariable Long id, @RequestBody OtModel model) {
        model.setId(id);
        return ResponseDto.of(OTDto.toDto(this.otService.update(model)), "Add ot");
    }

    @Transactional
    @DeleteMapping("{id}")
    public ResponseDto deleteById(@PathVariable Long id) {
        return ResponseDto.of(this.otService.deleteById(id) == false ? null : true, "Delete staff with id: " + id);
    }

    @Transactional
    @PatchMapping("change-status")
    public Object changeStatus(@RequestBody List<Long> ids, @RequestParam("status") RequestStatusUtil status) {
        return ResponseDto.of(otService.changeStatus(ids, status) == true ? true : null, "Update timekeeping success");
    }


}
