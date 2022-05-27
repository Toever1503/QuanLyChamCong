package com.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtModel {
    @ApiModelProperty(notes = "id cua OT", dataType = "Long", example = "1")
    private Long id;

    @ApiModelProperty(notes = "id cua Staff", dataType = "Long", example = "1")
    @NotNull
    private Long staff_id;
  
    @ApiModelProperty(notes = "Ghi chu tang ca de lam gi", dataType = "String", example = "fix bug")
    @NotNull
    @NotBlank
    private String note;

    @ApiModelProperty(notes = "thoi gian bat dau OT tinh theo milliseconds", dataType = "date", example = "124667348")
    @NotNull
    @Positive
    private Long time_start;

    @ApiModelProperty(notes = "thoi gian ket thuc cua OT tinh theo milliseconds", dataType = "Long", example = "57858583")
    @NotNull
    @Positive
    private Long time_end;

    @ApiModelProperty(notes = "thoi gian tao cua OT, tu dong tao", dataType = "date", example = "1")
    private java.util.Date time_created;

    @ApiModelProperty(notes = "he so luong khi lam OT", dataType = "float", example = "1.5")
    @Positive
    private Float multiply;

    @ApiModelProperty(notes = "Trang thai cua request (PENDING, REJECTED, APPROVED)", dataType = "enum", example = "PENDING")
    @NotNull
    @NotBlank
    private String status;
}
