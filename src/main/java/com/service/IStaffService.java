package com.service;

import com.config.jwt.JwtLoginResponse;
import com.config.jwt.JwtUserLoginModel;
import com.entity.Staff;
import com.model.StaffModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IStaffService extends IBaseService<Staff, StaffModel, Long> {
    Staff findByUsername(String username);
    JwtLoginResponse login(JwtUserLoginModel userLogin);
    List<Staff> findStaffAndTimekeep(Long id);
    Page<Staff> findStaffOfManager(Long managerId, Pageable pageable);

    boolean tokenFilter(String token, HttpServletRequest req);

}
