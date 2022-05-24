package com.model;

import com.dto.SalaryDto;
import com.entity.Salary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SalaryModel {
    private Long id;
    private int late_day;
    private int off_day;
    private int work_day;
    private int ot_hour;
    private int month;
    private double total_salary;


    //Model to Entity
    public static Salary toEntity(SalaryModel salary)
    {
        if(salary == null){
            return null;
        }else
            return Salary.builder().total_salary(salary.getTotal_salary()).id(salary.getId()).ot_hour(salary.getOt_hour()).off_day(salary.getOff_day()).work_day(salary.getWork_day()).late_day(salary.getLate_day()).month(salary.getMonth()).build();
    }
}
