package com.dto;

import com.entity.Staff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffDto {
    private Long staffId;

    private String staffName;
    private String email;
    private Date birthday;
    private Double salary;
    private String avatar;
    private Date createdAt;
    private String manager;
    private String position;

    public static StaffDto toDto(Staff entity) {
        if (entity == null) throw new RuntimeException("Entity is null");
        return StaffDto.builder()
                .staffId(entity.getStaffId())
                .staffName(entity.getStaffName())
                .email(entity.getEmail())
                .birthday(entity.getBirthday())
                .salary(entity.getSalary())
                .avatar(entity.getAvatar())
                .createdAt(entity.getCreatedAt())
                .manager(entity.getManager() != null ? entity.getManager().getStaffName() : null)
                .position(entity.getPosition().getPositionName())
                .build();
    }

}
