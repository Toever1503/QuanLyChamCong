package com.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffModel {
    @ApiModelProperty(notes = "id uuid cua nhan vien", dataType = "Long", example = "1")
    private Long staffId;

    @ApiModelProperty(notes = "ten cua nhan vien", dataType = "String", example = "Hau hom hinh")
    @NotNull
    @NotBlank
    private String staffName;

    @ApiModelProperty(notes = "email cua nhan vien", dataType = "String", example = "haunv@gmail.com")
    @Email
    private String email;

    @ApiModelProperty(notes = "mat khau dang nhap cua nhan vien", dataType = "String", example = "haun@123")
    private String password;

    @ApiModelProperty(notes = "ngay sinh cua nhan vien", dataType = "date", example = "2001/10/21")
    private Date birthday;

    @ApiModelProperty(value = "luong cua nhan vien", dataType = "double", example = "500$")
    @NotNull
    private Double salary;

    @ApiModelProperty(notes = "anh cua nhan vien", dataType = "String", example = "/image/hauxinhgai.jpg")
    private String avatar;

    @ApiModelProperty(notes = "ngay tao nhan vien, tu dong tao", dataType = "date", example = "2022/12/12")
    private Date createdAt;

    @ApiModelProperty(notes = "id cua quan ly truc tiep", dataType = "Long", example = "1")
    @NotNull
    @Positive
    private Long manager;

    @ApiModelProperty(notes = "id vi tri cua nhan vien", dataType = "Long", example = "4")
    @NotNull
    @Positive
    private Long position;
}
