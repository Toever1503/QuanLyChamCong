package com.dto;

import com.entity.Staff;
import com.entity.TimeLate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TimeLateDto {
    private Long id;

    private Long timeIn;

    private Long dayLate;

    private String note;

    private String status;

    private String staff;
    private Date timeCreated;

    //Entity to Dto
    public static TimeLateDto entityToDto(TimeLate timeLate) {
        return TimeLateDto.builder()
                .id(timeLate.getId())
                .timeIn(timeLate.getTimeIn())
                .dayLate(timeLate.getDayLate())
                .note(timeLate.getNote())
                .status(timeLate.getStatus())
                .staff(timeLate.getStaff().getStaffName())
                .timeCreated(timeLate.getTimeCreated())
                .build();
    }
}
