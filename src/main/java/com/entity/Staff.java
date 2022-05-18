package com.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "staffs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffId;

    @Column(name = "staff_name")
    private String staffName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "avatar")
    private String avatar;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Staff manager;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
}
