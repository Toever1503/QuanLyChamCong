package com.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtModel {
    private Long id;
    private Long staff_id;
    private Long time_start;
    private Long time_end;
    private Float multiply;
    private String status;
}
