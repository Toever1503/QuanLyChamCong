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

    private Long timeOut;

    private String note;

    private String status;

    private Long staffDtoId;

    public static TimeLateDto entityToDto(TimeLate timeLate) {
        return TimeLateDto.builder()
                .id(timeLate.getId())
                .timeIn(timeLate.getTimeIn())
                .timeOut(timeLate.getTimeOut())
                .note(timeLate.getNote())
                .status(timeLate.getStatus())
                .staffDtoId(timeLate.getStaff().getStaffId())
                .build();
    }
}
