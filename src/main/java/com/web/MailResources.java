package com.web;

import com.dto.ResponseDto;
import com.dto.SalaryDto;
import com.entity.Staff;
import com.service.IMailService;
import com.service.ISalaryService;
import com.service.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/mail")
public class MailResources {
    @Autowired
    IMailService mailService;
    @Autowired
    IStaffService staffService;
    @Autowired
    ISalaryService salaryService;

    @PostMapping("/all")
    public ResponseDto sendAllMail() throws MessagingException {
        int month = LocalDate.now().getMonth().getValue();
        List<SalaryDto> salaryDtoList = new ArrayList<>();
        for (Staff s: staffService.findAll()
             ) {
            SalaryDto salaryDto = SalaryDto.toDto(salaryService.calculateSalary(s.getStaffId()));
            mailService.sendHtmlMail(s.getEmail(),"Báo cáo lương tháng"+month,null,null,null,salaryDto);
            salaryDtoList.add(salaryDto);
        }
    return ResponseDto.of(salaryDtoList,"Mail sent to all employees");
    }

    @PostMapping("/invidual/{id}")
    public ResponseDto sendAMail(@PathVariable long id) throws MessagingException {
        int month = LocalDate.now().getMonth().getValue();
        Staff s = staffService.findById(id);
        SalaryDto salaryDto = SalaryDto.toDto(salaryService.calculateSalary(id));
        mailService.sendHtmlMail(s.getEmail(),"Báo cáo lương tháng "+month,null,null,null,salaryDto);
        return ResponseDto.of(salaryDto,"Mail sent to employee");
    }
}
