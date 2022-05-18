package com.dto;

import com.entity.Staff;
import com.model.RequestUtil;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OTDto {
    private Long id;
    private Long staff_id;
    private Long time_start;
    private Long time_end;
    private java.util.Date time_created;
    private Float multiply;
    private String status;
}
