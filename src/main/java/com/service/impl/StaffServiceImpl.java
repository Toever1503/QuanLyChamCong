package com.service.impl;

import com.Util.SecurityUtil;
import com.config.jwt.JwtLoginResponse;
import com.config.jwt.JwtProvider;
import com.config.jwt.JwtUserLoginModel;
import com.entity.Position;
import com.entity.Staff;
import com.model.StaffModel;
import com.repository.*;
import com.service.CustomUserDetail;
import com.service.IStaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
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
    private final IDayOffRepository dayOffRepository;

    private final IOTRepository otRepository;

    private final ITimekeepingRepository timekeepingRepository;

    private final ITimeLateRepository timeLateRepository;

    private final ISalaryRepository salaryRepository;




    private final Logger logger = LoggerFactory.getLogger(StaffServiceImpl.class);

    public StaffServiceImpl(IStaffRepository staffRepository, IPositionRepository positionRepository, JwtProvider jwtProvider, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, IDayOffRepository dayOffRepository, IOTRepository otRepository, ITimekeepingRepository timekeepingRepository, ITimeLateRepository timeLateRepository, ISalaryRepository salaryRepository) {
        this.staffRepository = staffRepository;
        this.positionRepository = positionRepository;
        this.dayOffRepository = dayOffRepository;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.otRepository = otRepository;
        this.timekeepingRepository = timekeepingRepository;
        this.timeLateRepository = timeLateRepository;
        this.salaryRepository = salaryRepository;
        try {
//            Staff administrator = new Staff(1l, "admin", "admin@admin.com", this.passwordEncoder.encode("1234"), new Date("2022-05-18"), 100.0, null, Calendar.getInstance().getTime(), null, this.positionRepository.findByPositionName(Position.ADMINISTRATOR));
            Staff administrator = this.staffRepository.findById(1l).get();
            administrator.setPassword(this.passwordEncoder.encode("1234"));
            this.staffRepository.save(administrator);
        } catch (Exception e) {
            logger.warn("administrator already exist");
        }
    }
    //Model to entity
    Staff toEntity(StaffModel model) {
        if (model == null) throw new RuntimeException("Staff model is null");
        return Staff.builder()
                .staffId(model.getStaffId())
                .staffName(model.getStaffName())
                .email(model.getEmail())
                .birthday(model.getBirthday())
                .salary(model.getSalary())
                .avatar(model.getAvatar())
                .position(this.positionRepository.findById(model.getPosition()).orElseThrow(() -> new RuntimeException("Position not found")))
                .manager(findById(SecurityUtil.getCurrentUserId()))
                .build();
    }


    @Override
    public List<Staff> findAll() {
        return null;
    }
    //Tìm tất cả nhân viên theo quản lí hoặc không // Find all staff by manager or else
    @Override
    public Page<Staff> findAll(Pageable page) {
        if (SecurityUtil.hasRole(Position.ADMINISTRATOR)) return this.staffRepository.findAll(page);
        return findStaffOfManager(SecurityUtil.getCurrentUser().getStaff().getStaffId(), page);
    }

    //Tìm nhân viên theo id// Find staff by id
    @Override
    public Staff findById(Long id) {
        return this.staffRepository.findById(id).orElseThrow(() -> new RuntimeException("Staff not found"));
    }
    //Thêm nhân viên // Add new staff
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
    //Cập nhật thông tin nhân viên // Edit staff information
    @Override
    public Staff update(StaffModel model) {
        Staff original = this.findById(model.getStaffId());
        if (model.getStaffId() == 1l) throw new RuntimeException("Can not update administrator");
        Staff staff = toEntity(model);
        if (model.getPassword() != null) staff.setPassword(this.passwordEncoder.encode(model.getPassword()));
        else staff.setPassword(original.getPassword());
        return this.staffRepository.save(staff);
    }
    //Xóa nhân viên theo id// Delete staff by id
    @Override
    public boolean deleteById(Long id) {
        if (id == 1l) throw new RuntimeException("Can not delete administrator");
        this.dayOffRepository.deleteAllByStaffStaffId(id);
        this.otRepository.deleteAllByStaffStaffId(id);
        this.salaryRepository.deleteAllByStaffStaffId(id);
        this.timekeepingRepository.deleteAllByStaffStaffId(id);
        this.timeLateRepository.deleteAllByStaffStaffId(id);
        this.staffRepository.deleteById(id);
        return true;
    }
    // Xóa danh sách nhân viên // Delete staffs by ids
    @Override
    public boolean deleteByIds(List<Long> ids) {
        ids.forEach(this::deleteById);
        return true;
    }
    // Tìm nhân viên theo tên // Find staff by username
    @Override
    public Staff findByUsername(String username) {
        return this.staffRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Staff not found"));
    }
    // Tạo token cho người dùng mới đăng nhập // Generate token for logged in user
    @Override
    public JwtLoginResponse login(JwtUserLoginModel userLogin) {
        UserDetails userDetail = new CustomUserDetail(this.findByUsername(userLogin.getUsername()));
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetail, userLogin.getPassword(), userDetail.getAuthorities()));
        long timeValid = userLogin.isRemember() ? 86400 * 7 : 1800l;
        return JwtLoginResponse.builder().token(this.jwtProvider.generateToken(userDetail.getUsername(), timeValid)).type("Bearer").authorities(userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())).timeValid(timeValid).build();
    }

    // Tìm tất cả nhân viên của quản lí // Find all staffs of manager
    @Override
    public Page<Staff> findStaffOfManager(Long managerId, Pageable page) {
        return staffRepository.findAllStaffForManager(managerId, page);
    }
    // Bảo mật bằng token // Token filter
    public boolean tokenFilter(String token, HttpServletRequest req) {
        String username = this.jwtProvider.getUsernameFromToken(token);
        System.out.println("username: " + username);
        CustomUserDetail userDetail = new CustomUserDetail(this.findByUsername(username));
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        return true;
    }
    // Lấy hồ sơ người dùng // Get staff profile
    @Override
    public Staff getProfile() {
        return this.findById(SecurityUtil.getCurrentUserId());
    }
}
