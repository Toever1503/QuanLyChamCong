package com.service;


import com.entity.Salary;
import com.model.SalaryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISalaryService extends IBaseService<Salary, SalaryModel,Long>{
    Salary calculateSalary(Long client_id);

    Page<Salary> calculateTotalSalaryForEmployee(Pageable page);

    List<Salary> calculateTotalSalaryForEmployee();

    Salary getMySalaryByMonth(int month);

}
