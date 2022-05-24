package com.web;

import com.Util.RequestStatusUtil;
import com.Util.SecurityUtil;
import com.dto.DayOffDTO;
import com.dto.OTDto;
import com.dto.ResponseDto;
import com.dto.TimeLateDto;
import com.entity.OtModel;
import com.entity.Position;
import com.service.OTService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
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
    @GetMapping("{id}")
    public ResponseDto getById(@PathVariable Long id) {
        return ResponseDto.of(OTDto.toDto(this.otService.findById(id)), "Get request OT by id: " + id);
    }

    @RolesAllowed(Position.ADMINISTRATOR)
    @Transactional
    @GetMapping("all-request/{staffId}")
    public ResponseDto getAllRequestsByStaffId(@PathVariable long staffId, Pageable page) {
        return ResponseDto.of(otService.findAllStaffRequests(staffId, page).map(OTDto::toDto), "Get all request time ots by staff");
    }

    @Transactional
    @GetMapping("my-request")
    public ResponseDto getAllMyRequest(Pageable page) {
        return ResponseDto.of(this.otService.findAllStaffRequests(SecurityUtil.getCurrentUserId(), page).map(OTDto::toDto), "Get all ots of manager: ");
    }


    @Transactional
    @PostMapping
    public ResponseDto add(@RequestBody OtModel model) {
        model.setId(null);
        return ResponseDto.of(OTDto.toDto(this.otService.add(model)), "Add request ot");
    }

    @Transactional
    @PutMapping("{id}")
    public ResponseDto update(@PathVariable Long id, @RequestBody OtModel model) {
        model.setId(id);
        return ResponseDto.of(OTDto.toDto(this.otService.update(model)), "Add request ot");
    }

    @Transactional
    @DeleteMapping("{id}")
    public ResponseDto deleteById(@PathVariable Long id) {
        return ResponseDto.of(this.otService.deleteById(id) == false ? null : true, "Delete request OT with id: " + id);
    }

    @Transactional
    @PatchMapping("change-status")
    public Object changeStatus(@RequestBody List<Long> ids, @RequestParam("status") RequestStatusUtil status) {
        return ResponseDto.of(this.otService.changeStatus(ids, status) == true ? true : null, "Update OT success");
    }

    @Transactional
    @GetMapping("get-request-by-date/{timein}/{timeout}")
    public ResponseDto getAllRequestsByDate(@PathVariable long timein, @PathVariable long timeout ,Pageable page) {
        return ResponseDto.of(this.otService.getAllRequestsByTime(timein,timeout, page).map(OTDto::toDto), "Get all request OT by date: " + timein + timeout);
    }


}
