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
    private String note;
    private Long time_start;
    private Long time_end;
    private java.util.Date timeCreated;
    private Float multiply;
    private String status;

    //Entity to dto
    public static OTDto toDto(OT entity) {
        if (entity == null) throw new RuntimeException("OT Entity is null");
        return OTDto.builder()
                .id(entity.getId())
                .staff(entity.getStaff().getStaffName())
                .note(entity.getNote())
                .time_start(entity.getTime_start())
                .time_end(entity.getTime_end())
                .timeCreated(entity.getTimeCreated())
                .multiply(entity.getMultiply())
                .status(entity.getStatus())
                .build();
    }
}
