package com.dto;

import com.entity.Staff;
import com.entity.TimeKeeping;
import com.model.RequestUtil;
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

    private Long timeOut;

    private String note;

    private String status;

    private Staff staff;

    public static TimeKeepingDto entityToDto(TimeKeeping entity) {
        return TimeKeepingDto.builder()
                .id(entity.getId())
                .timeIn(entity.getTimeIn())
                .timeOut(entity.getTimeOut())
                .note(entity.getNote())
                .status(entity.getStatus())
                .staff(entity.getStaff())
                .build();
    }
}
