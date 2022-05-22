package com.service.impl;

import com.Util.RequestStatusUtil;
import com.Util.SecurityUtil;
import com.Util.TimeUtil;
import com.entity.Position;
import com.entity.TimeLate;
import com.model.TimelateModel;
import com.repository.IStaffRepository;
import com.repository.ITimeLateRepository;
import com.service.ITimeLateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeLateServiceImpl implements ITimeLateService {
    final private ITimeLateRepository timeLateRepository;
    final private IStaffRepository staffRepository;

    public TimeLateServiceImpl(ITimeLateRepository timeLateRepository, IStaffRepository staffRepository) {
        this.timeLateRepository = timeLateRepository;
        this.staffRepository = staffRepository;
    }

    @Override
    public List<TimeLate> findAll() {
        return null;
    }
    //Tìm tất cả yêu cầu chấm công từ nhân viên theo quản lí// Find all late work request by manager id
    @Override
    public Page<TimeLate> findAll(Pageable page) {
        if (SecurityUtil.hasRole(Position.ADMINISTRATOR)) return this.timeLateRepository.findAll(page);
        return this.timeLateRepository.findStaffOfManager(SecurityUtil.getCurrentUser().getStaff().getStaffId(), page);
    }
    // Tìm yêu cầu làm muộn theo id // Find late work request by id
    @Override
    public TimeLate findById(Long id) {
        return timeLateRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }
    // Gửi yêu cầu làm muộn // Send late work request
    @Override
    public TimeLate add(TimelateModel model) {
        TimeLate timeLate = TimelateModel.modelToEntity(model);
        timeLate.setStaff(this.staffRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(() -> new RuntimeException("Staff Not found")));
        return timeLateRepository.save(timeLate);
    }

    @Override
    public List<TimeLate> add(List<TimelateModel> model) {
        return null;
    }
    // Cập nhật yêu cầu làm muộn // Update late work request
    @Override
    public TimeLate update(TimelateModel model) {
        TimeLate timeLate = findById(model.getId());
        timeLate.setId(model.getId());
        timeLate.setStatus(model.getStatus());
        timeLateRepository.save(timeLate);
        return timeLate;
    }
    // Xóa yêu cầu làm muộn theo id // Delete late work request by id
    @Override
    public boolean deleteById(Long id) {
        if (timeLateRepository.findById(id).isPresent()) {
            timeLateRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByIds(List<Long> id) {
        return false;
    }
    // Phê duyệt hoặc từ chối yêu cầu làm muộn // Approve of Reject late work request
    @Override
    public boolean changeStatus(List<Long> ids, RequestStatusUtil status) {
        ids.forEach(id -> {
            TimeLate original = findById(id);
            original.setStatus(status.toString());
            this.timeLateRepository.save(original);
        });
        return true;
    }
    //Tìm tất cả yêu cầu làm muộn từ nhân viên theo quản lí và thời gian// Find all late work request by manager id and time
    @Override
    public Page<TimeLate> getAllRequestsByDate(long date, Pageable page) {
        Long[] times = TimeUtil.getBeginAndLastTimeDate(date);
        return this.timeLateRepository.findAllRequestByDate(SecurityUtil.getCurrentUserId(), times[0], times[1], page);
    }
    // Tìm tất cả yêu cầu làm muộn của tôi // Find all my late work requests
    @Override
    public Page<TimeLate> findAllMyRequests(Pageable page) {
        return this.timeLateRepository.findAllByStaffStaffId(SecurityUtil.getCurrentUserId(), page);
    }
}
