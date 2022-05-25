package com.model;

import com.entity.DayOff;
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
public class DayOffModel {
    @ApiModelProperty(notes = "id uuid separate each day off", dataType = "Long", example = "1")
    private Long id;

    @ApiModelProperty(notes = "id của nhân viên gửi yêu cầu", dataType = "Long", example = "1")
    @NotNull
    private Long staff;

    @ApiModelProperty(notes = "note của nhân viên gửi yêu cầu", dataType = "Long", example = "1")
    @NotNull
    private String note;

    @ApiModelProperty(notes = "thời gian bắt đầu xin nghỉ, tính bằng miliseconds", dataType = "Long", example = "191481941298410")
    @Positive
    @NotNull
    private Long time_start;

    @ApiModelProperty(notes = "thời gian kết thúc xin nghỉ, tính bằng miliseconds", dataType = "date", example = "191481941298410")
    @Positive
    @NotNull
    private Long time_end;

    @ApiModelProperty(notes = "Thời gian tạo của dayoff, tự động tạo", dataType = "date", example = "1")
    private java.util.Date time_created;

    @ApiModelProperty(notes = "trạng thái của phiếu(PENDING, REJECTED, APPROVED)", dataType = "enum", example = "1")
    @NotNull
    @NotBlank
    private String status;

    //Entity to DTO
    public static DayOffModel toDto(DayOff entity) {
        if (entity == null) throw new RuntimeException("Entity is null");
        return DayOffModel.builder()
                .id(entity.getId())
                .note(entity.getNote())
                .time_start(entity.getTime_start())
                .time_end(entity.getTime_end())
                .time_created(entity.getTime_created())
                .status(entity.getStatus())
                .build();
    }


}
