package com.service.impl;

import com.Util.RequestStatusUtil;
import com.Util.SecurityUtil;
import com.Util.TimeUtil;
import com.entity.OT;
import com.entity.OtModel;
import com.entity.Position;
import com.repository.IOTRepository;
import com.repository.IStaffRepository;
import com.service.OTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class OTServiceImp implements OTService {
    @Autowired
    IOTRepository otRepository;
    @Autowired
    IStaffRepository staffRepository;

    @Override
    public List<OT> findAll() {
        return null;
    }
    //Tìm kiếm tất cả yêu cầu làm thêm giờ từ nhân viên theo quản lí // Find all overtime request from employee by manager id
    @Override
    public Page<OT> findAll(Pageable page) {
        if (SecurityUtil.hasRole(Position.ADMINISTRATOR)) return this.otRepository.findAll(page);
        return findAllRequestForManager(SecurityUtil.getCurrentUser().getStaff().getStaffId(), page);
    }
    //Model to Entity
    OT toEntity(OtModel model) {
        if (model == null) throw new RuntimeException("Ot Model is null");
        return OT.builder()
                .id(model.getId())
                .time_start(model.getTime_start())
                .time_end(model.getTime_end())
                .multiply(model.getMultiply())
                .status(model.getStatus())
                .build();
    }
    //Tìm yêu cầu làm thêm giờ theo id// Find overtime request by id
    @Override
    public OT findById(Long id) {
        return otRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }
    //Yêu cầu làm thêm giờ // Send overtime request
    @Override
    public OT add(OtModel model) {
        OT entity = toEntity(model);
        entity.setStaff(this.staffRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(() -> new RuntimeException("Staff Not found")));
        entity.setStatus(RequestStatusUtil.PENDING.name());
        entity.setTime_created(Calendar.getInstance().getTime());
        return this.otRepository.save(entity);
    }

    @Override
    public List<OT> add(List<OtModel> model) {
        return null;
    }

    @Override
    public OT update(OtModel model) {
//        OT entity = toEntity(model);
        return null;
    }
    //Xóa yêu cầu làm thêm giờ theo id // Delete overtime request by id
    @Override
    public boolean deleteById(Long id) {
        if (otRepository.findById(id).isPresent()) {
            otRepository.delete(otRepository.findById(id).get());
            return true;
        } else
            return false;
    }
    //Xóa nhiều yêu cầu làm thêm giờ theo id // Delete overtime requests by ids
    @Override
    public boolean deleteByIds(List<Long> id) {
        for (Long i : id
        ) {
            if (otRepository.findById(i).isPresent()) {
                otRepository.delete(otRepository.findById(i).get());
            } else
                return false;
        }
        return true;
    }
    //Phê duyệt, bác bỏ yêu cầu làm thêm giờ theo id// Approve Reject overtime requests by id
    @Override
    public boolean changeStatus(List<Long> ids, RequestStatusUtil status) {
        ids.forEach(id -> {
            OT original = this.findById(id);
            original.setStatus(status.name());
            this.otRepository.save(original);
        });
        return true;
    }
    //Tìm tất cả yêu cầu làm thêm giờ theo quản lí // Find all overtime requests by manager
    @Override
    public Page<OT> findAllRequestForManager(Long id, Pageable page) {
        return this.otRepository.findAllRequestForManager(id, page);
    }
    //Tìm tất cả yêu cầu làm thêm giờ theo quản lí và thời gian // Find all overtime requests by manager and time
    @Override
    public Page<OT> getAllRequestsByDate(long date, Pageable page) {
        Long[] times = TimeUtil.getBeginAndLastTimeDate(date);
        return this.otRepository.findAllRequestByDate(SecurityUtil.getCurrentUserId(), times[0], times[1], page);
    }
    //Tìm tất cả yêu cầu làm thêm giờ của tôi // Find all my overtime requests
    @Override
    public Page<OT> findAllStaffRequests(Long staffId, Pageable page) {
        return this.otRepository.findAllByStaffStaffId(staffId, page);
    }
}
