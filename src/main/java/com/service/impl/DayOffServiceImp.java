package com.service.impl;

import com.Util.RequestStatusUtil;
import com.Util.SecurityUtil;
import com.Util.TimeUtil;
import com.entity.DayOff;
import com.entity.Position;
import com.model.DayOffModel;
import com.repository.IDayOffRepository;
import com.repository.IStaffRepository;
import com.service.DayOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class DayOffServiceImp implements DayOffService {
    @Autowired
    IDayOffRepository dayOffRepository;
    @Autowired
    IStaffRepository staffRepository;

    DayOff toEntity(DayOffModel model) {
        if (model == null) throw new RuntimeException("DayOffModel is null");
        return DayOff.builder()
                .id(model.getId())
                .time_start(model.getTime_start())
                .time_end(model.getTime_end())
                .status(model.getStatus())
                .build();
    }

    @Override
    public List<DayOff> findAll() {
        return null;
    }

    @Override
    public Page<DayOff> findAll(Pageable page) {
        if (SecurityUtil.hasRole(Position.ADMINISTRATOR)) return this.dayOffRepository.findAll(page);
        return findAllRequestForManager(SecurityUtil.getCurrentUser().getStaff().getStaffId(), page);
    }

    @Override
    public DayOff findById(Long id) {
        return dayOffRepository.findById(id).orElseThrow(() -> new RuntimeException("DayOff Not found"));
    }

    @Override
    public DayOff add(DayOffModel model) {
        DayOff dayOff = this.toEntity(model);
        dayOff.setStaff(this.staffRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(() -> new RuntimeException("Staff Not found")));
        dayOff.setTime_created(Calendar.getInstance().getTime());
        dayOff.setStatus(RequestStatusUtil.PENDING.name());
        return this.dayOffRepository.save(dayOff);
    }

    @Override
    public List<DayOff> add(List<DayOffModel> model) {
        return null;
    }


    @Override
    public DayOff update(DayOffModel model) {
        DayOff dayOff = this.toEntity(model);
        dayOff.setStaff(this.staffRepository.findById(model.getStaff()).orElseThrow(() -> new RuntimeException("Staff Not found")));
        return this.dayOffRepository.save(dayOff);
    }


    @Override
    public boolean deleteById(Long id) {
        dayOffRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        ids.forEach(this::deleteById);
        return true;
    }

    @Override
    public boolean changeStatus(List<Long> ids, RequestStatusUtil status) {
        ids.forEach(id -> {
            DayOff original = this.findById(id);
            original.setStatus(status.name());
            this.dayOffRepository.save(original);
        });
        return true;
    }

    @Override
    public Page<DayOff> findAllRequestForManager(Long id, Pageable page) {
        return this.dayOffRepository.findAllRequestForManager(id, page);
    }

    @Override
    public Page<DayOff> getAllRequestsByDate(long date, Pageable page) {
        Long[] times = TimeUtil.getBeginAndLastTimeDate(date);
        return this.dayOffRepository.findAllRequestByDate(SecurityUtil.getCurrentUserId(), times[0], times[1], page);
    }

    @Override
    public Page<DayOff> findAllMyRequests(Pageable page) {
        return this.dayOffRepository.findAllByStaffStaffId(SecurityUtil.getCurrentUserId(), page);
    }
}
