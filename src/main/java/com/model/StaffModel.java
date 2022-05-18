package com.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffModel {
    private Long staffId;

    private String staffName;

    private String email;

    private String password;

    private Date birthday;

    private Double salary;

    private String avatar;

    private Date createdAt;
    private Long manager;
    private Long position;
}
