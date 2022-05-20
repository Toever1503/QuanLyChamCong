package com.web;

import com.dto.ResponseDto;
import com.dto.SalaryDto;
import com.service.ISalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/salary")
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
    @PostMapping("/add/{id}")
    public ResponseDto calculateSalary(@PathVariable("id") Long id){
        if (id!=null){
            return ResponseDto.of(salaryService.calculateSalary(id),"Post salary");
        }
        return ResponseDto.of(null, "Post");
    }

}
