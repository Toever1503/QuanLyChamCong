package com.model;

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

    private Long timeOut;

    private String note;

    private String status;

    private Long staffModelId;

    public static TimeLate modelToEntity(TimelateModel model){
        if(model == null) return null;
        return TimeLate.builder()
                .id(model.getId())
                .timeIn(model.getTimeIn())
                .timeOut(model.getTimeOut())
                .note(model.getNote())
                .status(model.getStatus())
                .staff(model.staffModelId == null ? null : Staff.builder().staffId(model.staffModelId).build())
                .build();
    }
}
