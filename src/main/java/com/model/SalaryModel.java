package com.model;

import com.dto.SalaryDto;
import com.entity.Salary;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SalaryModel {
    @ApiModelProperty(notes = "id uuid cua Salary", dataType = "Long", example = "1")
    private Long id;

    @ApiModelProperty(notes = "so ngay di lam muon", dataType = "Integer", example = "2")
    private int late_day;

    @ApiModelProperty(notes = "so ngay nghi", dataType = "Integer", example = "2")
    private int off_day;

    @ApiModelProperty(notes = "so ngay lam viec", dataType = "Integer", example = "20")
    private int work_day;

    @ApiModelProperty(notes = "so gio lam them", dataType = "Integer", example = "11")
    private int ot_hour;

    @ApiModelProperty(notes = "thang tinh luong", dataType = "Integer", example = "10")
    @NotNull
    @Positive
    private int month;

    @ApiModelProperty(notes = "tong so luong cua thang", dataType = "double", example = "450$")
    @NotNull
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
