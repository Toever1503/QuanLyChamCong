package com.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "day_offs")
public class DayOff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = Staff.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @Column(name ="note")
    private String note;

    @Column(name = "time_start")
    private Long time_start;

    @Column(name = "time_end")
    private Long time_end;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_created")
    private Date timeCreated;
    @Column(name = "status")
    private String status;
}
