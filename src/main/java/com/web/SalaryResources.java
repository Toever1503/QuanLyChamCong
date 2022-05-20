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
