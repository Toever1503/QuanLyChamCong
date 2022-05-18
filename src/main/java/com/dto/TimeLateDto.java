package com.dto;

import com.entity.Staff;
import com.entity.TimeLate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TimeLateDto {
    private Long id;

    private Long timeIn;

    private String note;

    private String status;

    private Long staff;

    public static TimeLateDto entityToDto(TimeLate timeLate) {
        return TimeLateDto.builder()
                .id(timeLate.getId())
                .timeIn(timeLate.getTimeIn())
                .note(timeLate.getNote())
                .status(timeLate.getStatus())
                .staff(timeLate.getStaff().getStaffId())
                .build();
    }
}
