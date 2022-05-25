package com.web;

import com.dto.ResponseDto;

import com.dto.SalaryDto;
import com.entity.Position;
import com.service.ISalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/salary")
public class SalaryResources {
    @Autowired
    ISalaryService salaryService;

    @GetMapping("/all/{id}")
    public ResponseDto getAllSalariesByUser(@PathVariable("id") Long id) {
        return ResponseDto.of(salaryService.findAll().stream().filter(salary -> salary.getStaff().getStaffId() == id).collect(Collectors.toList()), "Get all salaries by staff");
    }

    @GetMapping("my-salary/{month}")
    public ResponseDto getMySalaryByMonth(int month) {
        return ResponseDto.of(this.salaryService.getMySalaryByMonth(month), "Get salaries by staff by month");
    }

    @RolesAllowed({Position.ADMINISTRATOR})
    @GetMapping("all-staff-salaries")
    public ResponseDto getAllSalariesOfStaff(Pageable page) {
        return ResponseDto.of(this.salaryService.calculateTotalSalaryForEmployee(page).map(SalaryDto::toDto), "Get all salaries");
    }

}
