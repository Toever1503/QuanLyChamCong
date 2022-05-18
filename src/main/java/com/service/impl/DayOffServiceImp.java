package com.service.impl;

import com.Util.RequestStatusUtil;
import com.dto.DayOffDTO;
import com.dto.OTDto;
import com.entity.DayOff;
import com.entity.OT;
import com.repository.DayOffRepository;
import com.repository.OTRepository;
import com.repository.StaffRepository;
import com.service.DayOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DayOffServiceImp implements DayOffService {
    @Autowired
    DayOffRepository dayOffRepository;
    @Autowired
    StaffRepository staffRepository;

    @Override
    public List<DayOff> findAll() {
        return dayOffRepository.findAll();
    }

    @Override
    public Page<DayOff> findAll(Pageable page) {
        return dayOffRepository.findAll(page);
    }

    @Override
    public DayOff findById(Long id) {
        if (dayOffRepository.findById(id).isPresent())
            return dayOffRepository.findById(id).get();
        else
            return null;
    }

    @Override
    public DayOff add(DayOffDTO model) {
        return null;
    }

    @Override
    public List<DayOff> add(List<DayOffDTO> model) {
        return null;
    }


    @Override
    public DayOff update(DayOffDTO model) {
        return null;
    }


    @Override
    public boolean deleteById(Long id) {
        dayOffRepository.delete(dayOffRepository.findById(id).get());
        return true;
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        ids.forEach(this::deleteById);
        return true;
    }

    @Override
    public DayOff changeStatus(Long id, RequestStatusUtil status) {
        DayOff original = this.findById(id);
        original.setStatus(status.name());
        return this.dayOffRepository.save(original);
    }

    @Override
    public Page<DayOff> findAllDayOffByEmployeeId(Long employeeId, Pageable page) {
        return this.dayOffRepository.findAllByStaffStaffId(employeeId, page);
    }
}
