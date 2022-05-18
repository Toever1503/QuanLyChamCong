package com.model;

import com.entity.DayOff;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayOffModel {
    private Long id;
    private Long staff;
    private Long time_start;
    private Long time_end;
    private java.util.Date time_created;
    private String status;

    public static DayOffModel toDto(DayOff entity) {
        if (entity == null) throw new RuntimeException("Entity is null");
        return DayOffModel.builder()
                .id(entity.getId())
                .time_start(entity.getTime_start())
                .time_end(entity.getTime_end())
                .time_created(entity.getTime_created())
                .status(entity.getStatus())
                .build();
    }


}
