package com.web;

import com.config.jwt.JwtUserLoginModel;
import com.dto.ResponseDto;
import com.dto.StaffDto;
import com.model.StaffModel;
import com.service.IStaffService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;


@RestController
@RequestMapping("api/staffs")
public class StaffResources {
    private final IStaffService staffService;

    public StaffResources(IStaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public ResponseDto getAll(Pageable page) {
        return ResponseDto.of(staffService.findAll(page).map(StaffDto::toDto), "Get all staffs");
    }

    @Transactional
    @GetMapping("profile")
    public ResponseDto getProfile(){
        return ResponseDto.of(StaffDto.toDto(this.staffService.getProfile()), "Get profile");
    }

    @Transactional
    @PostMapping
    public ResponseDto add(@RequestBody StaffModel model) {
        model.setStaffId(null);
        return ResponseDto.of(StaffDto.toDto(this.staffService.add(model)), "Add staff");
    }

    @Transactional
    @PutMapping("{id}")
    public ResponseDto update(@PathVariable Long id, @RequestBody StaffModel model) {
        model.setStaffId(id);
        return ResponseDto.of(StaffDto.toDto(this.staffService.update(model)), "Update staff with id: " + id);
    }

    @Transactional
    @DeleteMapping("{id}")
    public ResponseDto delete(@PathVariable Long id) {
        return ResponseDto.of(this.staffService.deleteById(id) == false ? null : true, "Delete staff with id: " + id);
    }

    @Transactional
    @PostMapping("login")
    public ResponseDto login(@RequestBody JwtUserLoginModel userLoginModel) {
        return ResponseDto.of(this.staffService.login(userLoginModel), "Login ");
    }

    @Transactional
    @GetMapping("profile/{id}")
    public ResponseDto getStaffById(@PathVariable Long id) {
        return ResponseDto.of(StaffDto.toDto(this.staffService.findById(id)), "Get staff from id manager with id: " + id);
    }

}
