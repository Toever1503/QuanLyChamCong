package com.service.impl;

import com.Util.RequestStatusUtil;
import com.Util.SecurityUtil;

import com.Util.TimeUtil;
import com.entity.DayOff;
import com.entity.Position;
import com.model.DayOffModel;
import com.repository.IDayOffRepository;
import com.repository.IStaffRepository;
import com.repository.ITimekeepingRepository;
import com.service.DayOffService;
import com.service.ITimeKeepingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

@Service
public class DayOffServiceImp implements DayOffService {
    @Autowired
    IDayOffRepository dayOffRepository;
    @Autowired
    IStaffRepository staffRepository;
    @Autowired
    ITimekeepingRepository timekeepingRepository;

    //Model to Entity
    DayOff toEntity(DayOffModel model) {
        if (model == null) throw new RuntimeException("DayOffModel is null");
        return DayOff.builder()
                .id(model.getId())
                .note(model.getNote())
                .time_start(model.getTime_start())
                .time_end(model.getTime_end())
                .status(model.getStatus())
                .build();
    }

    @Override
    public List<DayOff> findAll() {
        return null;
    }

    //Tìm kiếm tất cả yêu cầu nghỉ từ nhân viên theo quản lí // Find all day off request from employee by manager id
    @Override
    public Page<DayOff> findAll(Pageable page) {
        if (SecurityUtil.hasRole(Position.ADMINISTRATOR)) return this.dayOffRepository.findAll(page);
        return findAllRequestForManager(SecurityUtil.getCurrentUser().getStaff().getStaffId(), page);
    }

    //Tìm kiếm yêu cầu nghỉ từ nhân viên theo mã // Find day off request from employee by  id
    @Override
    public DayOff findById(Long id) {
        return dayOffRepository.findById(id).orElseThrow(() -> new RuntimeException("DayOff Not found"));
    }

    //Yêu cầu ngày nghỉ từ nhân viên // Add day off request
    @Override
    public DayOff add(DayOffModel model) {
        DayOff dayOff = this.toEntity(model);
        dayOff.setStaff(this.staffRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(() -> new RuntimeException("Staff Not found")));
        if(!checkCheckIn(model.getTime_start(), model.getTime_end(),dayOff.getStaff().getStaffId())){
            dayOff.setTimeCreated(Calendar.getInstance().getTime());
            dayOff.setStatus(RequestStatusUtil.PENDING.name());
            return this.dayOffRepository.save(dayOff);
        }else {
            return null;
        }
    }

    @Override
    public List<DayOff> add(List<DayOffModel> model) {
        return null;
    }

    //Cập nhật yêu cầu nghỉ từ nhân viên // Edit day off request
    @Override
    public DayOff update(DayOffModel model) {
        DayOff dayOff = this.toEntity(model);
        dayOff.setStaff(this.staffRepository.findById(model.getStaff()).orElseThrow(() -> new RuntimeException("Staff Not found")));
            return this.dayOffRepository.save(dayOff);
    }

    //Xóa yêu cầu nghỉ// Delete day off request
    @Override
    public boolean deleteById(Long id) {
        dayOffRepository.deleteById(id);
        return true;
    }

    //Xóa nhiều yêu cầu nghỉ// Delete day off requests
    @Override
    public boolean deleteByIds(List<Long> ids) {
        ids.forEach(this::deleteById);
        return true;
    }

    //Phê duyệt, hủy bỏ yêu cầu nghỉ // Approve Reject day off request
    @Override
    public boolean changeStatus(List<Long> ids, RequestStatusUtil status) {
        ids.forEach(id -> {
            DayOff original = this.findById(id);
            original.setStatus(status.name());
            this.dayOffRepository.save(original);
        });
        return true;
    }

    //Tìm kiếm tất cả yêu cầu nghỉ từ nhân viên theo id quản lí // Find all day off request from employee by manager id
    @Override
    public Page<DayOff> findAllRequestForManager(Long id, Pageable page) {
        return this.dayOffRepository.findAllRequestForManager(id, page);
    }

    //Tìm kiếm tất cả yêu cầu nghỉ từ nhân viên theo quản lí, thời gian // Find all day off request from employee by manager id and time
    @Override
    public Page<DayOff> getAllRequestsByTime(long timein, long timeout, Pageable page) {
        return this.dayOffRepository.findAllRequestByDate(SecurityUtil.getCurrentUserId(), timein, timeout, page);
    }

    //Tìm tất cả yêu cầu nghỉ của bản thân // Find all day off request of current user
    @Override
    public Page<DayOff> findAllStaffRequests(Long staffId, Pageable page) {
        return this.dayOffRepository.findAllByStaffStaffId(staffId, page);
    }

    @Override
    public boolean checkCheckIn(Long timestart, Long timeend, Long staffId) {
            if(timekeepingRepository.findTopByStaffStaffIdAndStatusOrderByTimeInDesc(staffId,RequestStatusUtil.APPROVED.toString())!= null){
                Long checkIn = timekeepingRepository.findTopByStaffStaffIdAndStatusOrderByTimeInDesc(staffId,RequestStatusUtil.APPROVED.toString()).getTimeIn();
                if(checkIn == null){
                    return false;
                }
                if(checkIn > timestart && checkIn < timeend){
                    return true;
                }else
                    return false;
            }else
                return false;
        }
}
