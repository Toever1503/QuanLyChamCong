package com.dto;

import com.entity.DayOff;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayOffDTO {
    private Long id;
    private Long staff_id;
    private Long time_start;
    private Long time_end;
    private java.util.Date time_created;
    private String status;

    public static DayOffDTO toDto(DayOff entity){
        if(entity == null) throw new RuntimeException("Entity is null");
        return DayOffDTO.builder()
                .id(entity.getId())
                .build();
    }


}
