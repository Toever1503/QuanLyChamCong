package com.model;

import com.dto.StaffDto;
import com.entity.Staff;
import com.entity.TimeLate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TimelateModel {
    private Long id;

    private Long timeIn;

    private String note;

    private String status;

    private Long staff;

    public static TimeLate modelToEntity(TimelateModel model){
        if(model == null) return null;
        return TimeLate.builder()
                .id(model.getId())
                .timeIn(model.getTimeIn())
                .note(model.getNote())
                .status(model.getStatus())
                .build();
    }
}
