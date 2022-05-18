package com.service.impl;

import com.config.jwt.JwtLoginResponse;
import com.config.jwt.JwtProvider;
import com.config.jwt.JwtUserLoginModel;
import com.entity.Position;
import com.entity.Staff;
import com.model.StaffModel;
import com.repository.IPositionRepository;
import com.repository.IStaffRepository;
import com.service.CustomUserDetail;
import com.service.IStaffService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StaffServiceImpl implements IStaffService {
    private final IStaffRepository staffRepository;
    private final IPositionRepository positionRepository;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public StaffServiceImpl(IStaffRepository staffRepository, IPositionRepository positionRepository, JwtProvider jwtProvider, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.staffRepository = staffRepository;
        this.positionRepository = positionRepository;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
//
//        Staff staff = this.staffRepository.findById(1L).orElseThrow(() -> new RuntimeException("Staff not found"));
//        staff.setPassword(this.passwordEncoder.encode("1234"));
//        this.staffRepository.save(staff);
    }

    Staff toEntity(StaffModel model) {
        if (model == null) throw new RuntimeException("Staff model is null");
        return Staff.builder().staffId(model.getStaffId()).staffName(model.getStaffName()).email(model.getEmail()).birthday(model.getBirthday()).salary(model.getSalary()).avatar(model.getAvatar()).position(this.positionRepository.findById(model.getPosition()).orElseThrow(() -> new RuntimeException("Position not found"))).manager(this.findById(model.getManager())).build();
    }


    @Override
    public List<Staff> findAll() {
        return null;
    }

    @Override
    public Page<Staff> findAll(Pageable page) {
        return this.staffRepository.findAll(page);
    }

    @Override
    public Staff findById(Long id) {
        return this.staffRepository.findById(id).orElseThrow(() -> new RuntimeException("Staff not found"));
    }

    @Override
    public Staff add(StaffModel model) {
        Staff staff = toEntity(model);
        staff.setCreatedAt(Calendar.getInstance().getTime());
        staff.setPassword(this.passwordEncoder.encode(model.getPassword()));
        return this.staffRepository.save(staff);
    }

    @Override
    public List<Staff> add(List<StaffModel> model) {
        return null;
    }

    @Override
    public Staff update(StaffModel model) {
        Staff original = this.findById(model.getStaffId());
        if (model.getStaffId() == 1l) throw new RuntimeException("Can not update administrator");
        Staff staff = toEntity(model);
        if (model.getPassword() != null) staff.setPassword(this.passwordEncoder.encode(model.getPassword()));
        else staff.setPassword(original.getPassword());
        return this.staffRepository.save(staff);
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == 1l) throw new RuntimeException("Can not delete administrator");
        this.staffRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        ids.forEach(this::deleteById);
        return true;
    }

    @Override
    public Staff findByUsername(String username) {
        return this.staffRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Staff not found"));
    }

    @Override
    public JwtLoginResponse login(JwtUserLoginModel userLogin) {
        UserDetails userDetail = new CustomUserDetail(this.findByUsername(userLogin.getUsername()));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetail, userLogin.getPassword(), userDetail.getAuthorities()));
        long timeValid = userLogin.isRemember() ? 86400 * 7 : 1800l;
        return JwtLoginResponse.builder()
                .token(jwtProvider.generateToken(userLogin.getUsername(), timeValid))
                .type("Bearer")
                .authorities(userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .timeValid(timeValid)
                .build();
    }
}
