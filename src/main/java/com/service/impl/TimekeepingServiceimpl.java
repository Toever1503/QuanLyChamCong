package com.service.impl;

import com.Util.SecurityUtil;

import com.Util.TimeUtil;
import com.entity.Position;
import com.entity.TimeKeeping;
import com.Util.RequestStatusUtil;
import com.model.TimeKeepingModel;
import com.repository.IStaffRepository;
import com.repository.ITimekeepingRepository;
import com.service.ITimeKeepingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimekeepingServiceimpl implements ITimeKeepingService {
    private final ITimekeepingRepository timekeepingRepository;
    private final IStaffRepository staffRepository;

    public TimekeepingServiceimpl(ITimekeepingRepository timekeepingRepository, IStaffRepository staffRepository) {
        this.timekeepingRepository = timekeepingRepository;
        this.staffRepository = staffRepository;
    }

    @Override
    public List<TimeKeeping> findAll() {
        return null;
    }
    // Tìm tất cả yêu cầu chấm công từ nhân viên theo quản lí // Find all attendant request by staff by manager
    @Override
    public Page<TimeKeeping> findAll(Pageable page) {
        if (SecurityUtil.hasRole(Position.ADMINISTRATOR)) return this.timekeepingRepository.findAll(page);
        return this.timekeepingRepository.findAllRequestForManager(SecurityUtil.getCurrentUser().getStaff().getStaffId(), page);
    }
    // Tìm yêu cầu chấm công theo id // Find attendant request by id
    @Override
    public TimeKeeping findById(Long id) {
        return timekeepingRepository.findById(id).orElseThrow((() -> new RuntimeException("Not found")));
    }
    // Gửi yêu cầu chấm công // Send attendant request
    @Override
    public TimeKeeping add(TimeKeepingModel model) {
        TimeKeeping timeKeeping = TimeKeepingModel.modelToEntity(model);
        timeKeeping.setStaff(this.staffRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(() -> new RuntimeException("Staff Not found")));
        return timekeepingRepository.save(timeKeeping);
    }

    @Override
    public List<TimeKeeping> add(List<TimeKeepingModel> model) {
        return null;
    }


    @Override
    public TimeKeeping update(TimeKeepingModel model) {
        return null;
    }
    // Xóa yêu cầu chấm công theo id // Delete attendant request by id
    @Override
    public boolean deleteById(Long id) {
        timekeepingRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean deleteByIds(List<Long> id) {
        return false;
    }
    //Phê duyệt, từ chối yêu cầu chấm công // Approve or Reject attendant request
    @Override
    public boolean changeStatus(List<Long> ids, RequestStatusUtil status) {
        ids.forEach(id -> {
            TimeKeeping original = findById(id);
            original.setStatus(status.toString());
            this.timekeepingRepository.save(original);
        });
        return true;
    }
    // Tìm tất cả yêu cầu chấm công từ nhân viên theo quản lí và thời gian // Find all attendant request by staff by manager and time
    @Override
    public Page<TimeKeeping> getAllRequestsByTime(long timein, long timeout, Pageable page) {
        return this.timekeepingRepository.findAllRequestByDate(SecurityUtil.getCurrentUserId(), timein, timeout, page);
    }
    // Tìm tất cả yêu cầu chấm công của tôi // Find all my attendant request
    @Override
    public Page<TimeKeeping> findAllStaffRequests(Long staffId,Pageable page) {
        return this.timekeepingRepository.findAllByStaffStaffId(staffId, page);
    }
}
