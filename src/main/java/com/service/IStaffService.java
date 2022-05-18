package com.service;

import com.config.jwt.JwtLoginResponse;
import com.config.jwt.JwtUserLoginModel;
import com.entity.Staff;
import com.model.StaffModel;

import javax.servlet.http.HttpServletRequest;

public interface IStaffService extends IBaseService<Staff, StaffModel, Long> {
    Staff findByUsername(String username);
    JwtLoginResponse login(JwtUserLoginModel userLogin);

    boolean tokenFilter(String token, HttpServletRequest req);
}
