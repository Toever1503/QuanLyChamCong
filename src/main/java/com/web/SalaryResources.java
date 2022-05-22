package com.web;

import com.dto.ResponseDto;

import com.entity.Position;
import com.service.ISalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;


@RestController
@RequestMapping("api/salary")
public class SalaryResources {
    @Autowired
    ISalaryService salaryService;

    @GetMapping("/all")
    public ResponseDto getAllSalaries(Pageable pageable){
        return ResponseDto.of(salaryService.findAll(pageable).map(SalaryDto::toDto),"Get all salaries");
    }
    @GetMapping("/all/{id}")
    public ResponseDto getAllSalariesByUser(@PathVariable("id") Long id){
        return ResponseDto.of(salaryService.findAll().stream().filter(salary -> salary.getStaff().getStaffId()==id).collect(Collectors.toList()),"Get all salaries by staff");
    }

    @GetMapping("my-salary/{month}")
    public ResponseDto getMySalaryByMonth(int month) {
        return ResponseDto.of(this.salaryService.getMySalaryByMonth(month), "Get salaries by staff by month");
    }

    @RolesAllowed({Position.ADMINISTRATOR})
    @GetMapping("all-staff-salaries")
    public ResponseDto getAllSalaries(Pageable page) {
        return ResponseDto.of(this.salaryService.calculateTotalSalaryForEmployee(page), "Get all salaries");
    }

}
