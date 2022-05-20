package com.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "salary")
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "late_day")
    private int late_day;
    @Column(name = "off_day")
    private int off_day;
    @Column(name = "work_day")
    private int work_day;
    @Column(name = "ot_hour")
    private int ot_hour;
    @Column(name = "month")
    private int month;
    @Column(name = "total_salary")
    private double total_salary;
    @ManyToOne(targetEntity = Staff.class)
    @JoinColumn(name = "staff_id")
    private Staff staff;

}
