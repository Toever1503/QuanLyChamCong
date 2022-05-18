package com.dto;

import lombok.*;

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
}
