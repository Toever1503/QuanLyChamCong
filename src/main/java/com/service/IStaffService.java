package com.service;

import com.config.jwt.JwtLoginResponse;
import com.config.jwt.JwtUserLoginModel;
import com.entity.Staff;
import com.model.StaffModel;

public interface IStaffService extends IBaseService<Staff, StaffModel, Long> {
    Staff findByUsername(String username);
    JwtLoginResponse login(JwtUserLoginModel userLogin);
}
