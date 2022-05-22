package com.dto;

import com.entity.Salary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SalaryDto {
    private Long id;
    private int late_day;
    private int off_day;

    private Map<String, Object> work_day;
    private Map<String, Object> ot_hour;
    private int month;
    private double total_salary;

    //Entity to dto
    public static SalaryDto toDto(Salary salary) {
        if (salary == null) throw new RuntimeException("Entity Salary is null");
        Double income = salary.getStaff().getSalary();
        Double incomePerHour = income / 20 / 24;

        Map<String, Object> work_day = new HashMap<>();
        work_day.put("day", salary.getWork_day());
        work_day.put("salary", salary.getWork_day() * (income / 20));

        Map<String, Object> ot_hour = new HashMap<>();
        ot_hour.put("hour", salary.getOt_hour());
        ot_hour.put("salary", salary.getOt_hour() * incomePerHour);
        return SalaryDto.builder()
                .id(salary.getId())
                .late_day(salary.getLate_day())
                .off_day(salary.getOff_day())
                .work_day(work_day)
                .ot_hour(ot_hour)
                .month(salary.getMonth())
                .total_salary(salary.getTotal_salary())
                .build();
    }
}
