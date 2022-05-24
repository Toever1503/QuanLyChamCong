package com.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtModel {
    private Long id;
    private Long staff;
    private Long time_start;
    private Long time_end;
    private java.util.Date time_created;
    private Float multiply;
    private String status;

}
