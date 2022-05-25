package com.service;


import com.entity.Salary;
import com.model.SalaryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISalaryService extends IBaseService<Salary, SalaryModel,Long>{
    Salary calculateSalary(Long client_id);

    Page<Salary> calculateTotalSalaryForEmployee(Pageable page);

    Salary getMySalaryByMonth(int month);

    Salary getSalaryOfStaff(Long staffId);

}
