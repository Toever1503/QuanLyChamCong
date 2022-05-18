package com.entity;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "positions")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Position {
    public static String ADMINISTRATOR = "ADMINISTRATOR";
    public static String HR_MANAGER = "HR_MANAGER";
    public static String LEADER = "LEADER";
    public static String STAFF = "STAFF";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long positionId;


    @Column(name = "position_name", unique = true)
    private String positionName;
}
