package com.dto;

import com.entity.OT;
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
    private String staff;
    private Long time_start;
    private Long time_end;
    private java.util.Date time_created;
    private Float multiply;
    private String status;

    public static OTDto toDto(OT entity) {
        if (entity == null) throw new RuntimeException("OT Entity is null");
        return OTDto.builder()
                .id(entity.getId())
                .staff(entity.getStaff().getStaffName())
                .time_start(entity.getTime_start())
                .time_end(entity.getTime_end())
                .time_created(entity.getTime_created())
                .multiply(entity.getMultiply())
                .status(entity.getStatus())
                .build();
    }
}
