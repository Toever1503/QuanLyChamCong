package com.service;

import com.dto.SalaryDto;
import com.entity.Salary;
import com.model.SalaryModel;

public interface ISalaryService extends IBaseService<Salary, SalaryModel,Long>{
    SalaryDto calculateSalary(Long client_id);
}
