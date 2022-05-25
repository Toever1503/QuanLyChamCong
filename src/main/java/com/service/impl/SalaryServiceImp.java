package com.service.impl;

import com.Util.RequestStatusUtil;
import com.Util.SecurityUtil;
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
import java.util.Calendar;
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
        return null;
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

    //Tính toán bảng lương theo tháng của nhân viên // Calculate salary by month of employee
    @Override
    public Salary calculateSalary(Long client_id) {
        Salary salaryModel = new Salary();
        Staff staff = this.staffRepository.findById(client_id).orElseThrow(() -> new RuntimeException("Not found satff with id " + client_id));
        if (salaryRepository.findAll().stream().filter(salary -> salary.getStaff().getStaffId() == client_id).filter(salary -> salary.getMonth() == LocalDate.now().getMonthValue()).findAny().isPresent()) {
            salaryModel = salaryRepository.findAll().stream().filter(salary -> salary.getStaff().getStaffId() == client_id).filter(salary -> salary.getMonth() == LocalDate.now().getMonthValue()).findAny().get();
            salaryModel.setId(salaryModel.getId());
        }
        double total_price = 0D;
        double total_minus = 0D;

        int total_ot = 0;
        if (OTrepository.findById(client_id).isPresent()) {
            List<OT> otList = OTrepository.findAll().stream().filter(ot -> ot.getStaff().getStaffId() == client_id).filter(ot -> ot.getStatus().equals(RequestStatusUtil.APPROVED.toString())).collect(Collectors.toList());
            for (OT o : otList
            ) {
                total_ot += -(Math.round((o.getTime_end() - o.getTime_start()) / 3600000));
                total_price += -(o.getMultiply() * Math.round((o.getTime_end() - o.getTime_start()) / 3600000));
            }
        }

        Double incomePerHour = staff.getSalary() / 20 / 24;

        salaryModel.setLate_day(timeLateRepository.findAll().stream().filter(timeLate -> timeLate.getStaff().getStaffId() == client_id).filter(timeLate -> timeLate.getStatus().equals(RequestStatusUtil.APPROVED.toString())).collect(Collectors.toList()).size());
        salaryModel.setOt_hour(total_ot);
        salaryModel.setOff_day(dayOffRepository.findAll().stream().filter(dayOff -> dayOff.getStaff().getStaffId() == client_id).filter(dayOff -> dayOff.getStatus().equals(RequestStatusUtil.APPROVED.toString())).collect(Collectors.toList()).size());
        salaryModel.setWork_day(timekeepingRepository.findAll().stream().filter(timeKeeping -> timeKeeping.getStaff().getStaffId() == client_id).filter(timeKeeping -> timeKeeping.getStatus().equals(RequestStatusUtil.APPROVED.toString())).collect(Collectors.toList()).size());
        salaryModel.setMonth(LocalDate.now().getMonthValue());

        salaryModel.setTotal_salary(salaryModel.getWork_day() * (staff.getSalary() / 20) + (salaryModel.getOt_hour() * incomePerHour));
        salaryModel.setStaff(staffRepository.findById(client_id).get());

        return salaryRepository.save(salaryModel);
    }

    //Tính tổng lương quản lí cần trả // Calculate total amount of manager's employee salary
    @Override
    public Page<Salary> calculateTotalSalaryForEmployee(Pageable page) {
        List<Long> staffIds = new ArrayList<>();
        this.staffRepository.findAll(page)
                .forEach(staff -> {
                    staffIds.add(staff.getStaffId());
                    this.calculateSalary(staff.getStaffId());
                });
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        return this.salaryRepository.findAllByStaffStaffIdInAndMonth(staffIds, currentMonth, page);
    }


    //Tính lương nhân viên theo tháng // Calculate employee salary by month
    @Override
    public Salary getMySalaryByMonth(int month) {
        Long staffId = SecurityUtil.getCurrentUserId();
        return this.salaryRepository.findByStaffStaffIdAndMonth(staffId, month).orElse(this.calculateSalary(staffId));
    }

    @Override
    public Salary getSalaryOfStaff(Long staffId) {
        if (salaryRepository.findTopByStaffStaffIdOrderByIdDesc(staffId).isPresent())
            return salaryRepository.findTopByStaffStaffIdOrderByIdDesc(staffId).get();
        else
            return null;
    }

}
