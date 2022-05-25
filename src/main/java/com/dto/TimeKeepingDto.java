package com.dto;

import com.entity.Staff;
import com.entity.TimeKeeping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeKeepingDto {
    private Long id;

    private Long timeIn;

    private String note;

    private String status;

    private String staff;
    private Date timeCreated;

    //Entity to DTO
    public static TimeKeepingDto entityToDto(TimeKeeping entity) {
        return TimeKeepingDto.builder()
                .id(entity.getId())
                .timeIn(entity.getTimeIn())
                .timeCreated(entity.getTimeCreated())
                .note(entity.getNote())
                .status(entity.getStatus())
                .staff(entity.getStaff().getStaffName())
                .build();
    }
}
