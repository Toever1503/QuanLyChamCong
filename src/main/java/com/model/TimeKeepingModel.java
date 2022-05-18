package com.model;

import com.entity.Staff;
import com.entity.TimeKeeping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeKeepingModel {
    private Long id;

    private Long timeIn;

    private Long timeOut;

    private String note;

    private RequestUtil status;

    private Staff staff;

    public static TimeKeeping modelToEntity(TimeKeepingModel model){
        if (model == null) return null;
        return TimeKeeping.builder()
                .id(model.getId())
                .timeIn(model.getTimeIn())
                .timeOut(model.getTimeOut())
                .note(model.getNote())
                .status(model.getStatus() == null ? RequestUtil.PENDING.toString() : RequestUtil.PENDING.toString())
                .staff(model.getStaff() == null ? null : model.getStaff())
                .build();
    }
}
