package com.model;

import com.dto.StaffDto;
import com.entity.Staff;
import com.entity.TimeLate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TimelateModel {
    @ApiModelProperty(notes = "id uuid cua thoi gian di muon", dataType = "Long", example = "1")
    private Long id;

    @ApiModelProperty(notes = "thoi gian di muon", dataType = "Long", example = "57587585")
    @NotNull
    @Positive
    private Long timeIn;
  
    @ApiModelProperty(notes = "Ngay xin di muon", dataType = "Long", example = "516161561")
    @NotNull
    @Positive
    private Long dayLate;

    @ApiModelProperty(notes = "ghi chu khi di muon", dataType = "String", example = "xe hong")
    @NotBlank
    @NotNull
    private String note;

    @ApiModelProperty(notes = "trang thai cua request(PENDING, REJECTED, APPROVED)", dataType = "String", example = "PENDING")
    @NotNull
    @NotBlank
    private String status;

    @ApiModelProperty(notes = "id cua nhan vien", dataType = "Long", example = "4")
    @NotNull
    @Positive
    private Long staff;

    //Model to entity
    public static TimeLate modelToEntity(TimelateModel model){
        if(model == null) return null;
        return TimeLate.builder()
                .id(model.getId())
                .timeIn(model.getTimeIn())
                .dayLate(model.getDayLate())
                .note(model.getNote())
                .status(model.getStatus())
                .build();
    }
}
