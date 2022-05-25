package com.model;

import com.Util.RequestStatusUtil;
import com.entity.Staff;
import com.entity.TimeKeeping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeKeepingModel {
    @ApiModelProperty(notes = "id uuid cua ngay cham cong", dataType = "Long", example = "1")
    private Long id;

    @ApiModelProperty(notes = "ngay cham cong", dataType = "Long", example = "527575758")
    @NotNull
    private Long timeIn;

    @ApiModelProperty(notes = "ghi chu cua ngay di lam, co the khong can ghi chu van khong sao", dataType = "String", example = "khong co gi de viet")
    private String note;

    @ApiModelProperty(notes = "trang thai gui request(PENDING, REJECTED, APPROVED)", dataType = "Enum", example = "PENDING")
    @NotBlank
    @NotNull
    private RequestStatusUtil status;

    @ApiModelProperty(notes = "id cua nhan vien", dataType = "Long", example = "1")
    @NotNull
    @Positive
    private Long staff;

    //Model to entity
    public static TimeKeeping modelToEntity(TimeKeepingModel model){
        if (model == null) return null;
        return TimeKeeping.builder()
                .id(model.getId())
                .timeIn(model.getTimeIn())
                .note(model.getNote())
                .status(model.getStatus() == null ? RequestStatusUtil.PENDING.toString() : RequestStatusUtil.PENDING.toString())
                .build();
    }
}
