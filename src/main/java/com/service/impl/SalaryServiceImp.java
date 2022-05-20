package com.service.impl;

import com.Util.RequestStatusUtil;
import com.dto.SalaryDto;
import com.entity.DayOff;
import com.entity.OT;
import com.entity.Salary;
import com.entity.Staff;
import com.model.SalaryModel;
import com.repository.*;
import com.service.ISalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalaryServiceImp implements ISalaryService {
    @Autowired
    ISalaryRepository salaryRepository;
    @Autowired
    ITimeLateRepository timeLateRepository;
    @Autowired
    IDayOffRepository dayOffRepository;
    @Autowired
    ITimekeepingRepository timekeepingRepository;
    @Autowired
    IOTRepository OTrepository;
    @Autowired
    IStaffRepository staffRepository;
    @Override
    public List<Salary> findAll() {
        return salaryRepository.findAll();
    }

    @Override
    public Page<Salary> findAll(Pageable page) {
        return salaryRepository.findAll(page);
    }

    @Override
    public Salary findById(Long id) {
        if (salaryRepository.findById(id).isPresent())
         return salaryRepository.findById(id).get();
        else
            return null;
    }

    @Override
    public Salary add(SalaryModel model) {
        return salaryRepository.save(SalaryModel.toEntity(model));
    }

    @Override
    public List<Salary> add(List<SalaryModel> model) {
        List<Salary> salaries = new ArrayList<>();
        for (SalaryModel m: model
             ) {
            salaries.add(salaryRepository.save(SalaryModel.toEntity(m)));
        }
        return salaries;
    }

    @Override
    public Salary update(SalaryModel model) {
        salaryRepository.save(SalaryModel.toEntity(model));
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public boolean deleteByIds(List<Long> id) {
        return false;
    }

    @Override
    public SalaryDto calculateSalary(Long client_id) {
//        Salary salaryModel = salaryRepository.findAll().stream().filter(salary -> salary.getStaff().getStaffId()==clie;
        Salary salaryModel = new Salary();
        if(salaryRepository.findAll().stream().filter(salary -> salary.getStaff().getStaffId()==client_id).filter(salary -> salary.getMonth() == LocalDate.now().getMonthValue()).findAny().isPresent()){
            salaryModel = salaryRepository.findAll().stream().filter(salary -> salary.getStaff().getStaffId()==client_id).filter(salary -> salary.getMonth() == LocalDate.now().getMonthValue()).findAny().get();
            salaryModel.setId(salaryModel.getId());
        }
        double total_price = 0D;
        double total_minus = 0D;
        int total_ot= 0;
        if(OTrepository.findById(client_id).isPresent()) {
            List<OT> otList = OTrepository.findAll().stream().filter(ot -> ot.getStaff().getStaffId() == client_id).filter(ot -> ot.getStatus().equals(RequestStatusUtil.APPROVED.toString())).collect(Collectors.toList());
            for (OT o : otList
            ) {
                total_ot += -(Math.round((o.getTime_end() - o.getTime_start()) / 3600000));
                total_price += -(o.getMultiply() * Math.round((o.getTime_end() - o.getTime_start()) / 3600000));
            }
        }
        salaryModel.setLate_day(timeLateRepository.findAll().stream().filter(timeLate -> timeLate.getStaff().getStaffId()== client_id).filter(timeLate -> timeLate.getStatus().equals(RequestStatusUtil.APPROVED.toString())).collect(Collectors.toList()).size());
        salaryModel.setOt_hour(total_ot);
        salaryModel.setOff_day(dayOffRepository.findAll().stream().filter(dayOff -> dayOff.getStaff().getStaffId()== client_id).filter(dayOff -> dayOff.getStatus().equals(RequestStatusUtil.APPROVED.toString())).collect(Collectors.toList()).size());
        salaryModel.setWork_day(timekeepingRepository.findAll().stream().filter(timeKeeping -> timeKeeping.getStaff().getStaffId()==client_id).filter(timeKeeping -> timeKeeping.getStatus().equals(RequestStatusUtil.APPROVED.toString())).collect(Collectors.toList()).size());
        salaryModel.setMonth(LocalDate.now().getMonthValue());
        salaryModel.setTotal_salary(staffRepository.findById(client_id).get().getSalary()* salaryModel.getWork_day() + total_minus + total_price);
            salaryModel.setStaff(staffRepository.findById(client_id).get());
            total_minus -= salaryModel.getOff_day()*staffRepository.findById(client_id).get().getSalary();

            return SalaryDto.toDto(salaryRepository.save(salaryModel));
    }
}
