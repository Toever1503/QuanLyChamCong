package com.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "TimeKeeping")
public class TimeKeeping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timeIn")
    private Long timeIn;

    @Column(name ="note")
    private String note;

    @Column(name = "status")
    private String status;

        @OneToOne(fetch = FetchType.EAGER, optional = false)
        @JoinColumn(name = "staff_id", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        private Staff staff;

}
