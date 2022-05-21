package com.dto;

import com.entity.DayOff;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayOffDTO {
    private Long id;
    private String staff;
    private Long time_start;
    private Long time_end;
    private java.util.Date time_created;
    private String status;

    //Entity to DTO
    public static DayOffDTO toDto(DayOff entity) {
        if (entity == null) throw new RuntimeException("Entity is null");
        return DayOffDTO.builder()
                .id(entity.getId())
                .staff(entity.getStaff().getStaffName())
                .time_start(entity.getTime_start())
                .time_end(entity.getTime_end())
                .time_created(entity.getTime_created())
                .status(entity.getStatus())
                .build();
    }


}
