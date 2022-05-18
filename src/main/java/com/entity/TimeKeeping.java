package com.entity;

import com.model.RequestUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "timeOut")
    private Long timeOut;

    @Column(name ="note")
    private String note;

    @Column(name = "status")
    private String status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_id")
    private Staff staff;

}
