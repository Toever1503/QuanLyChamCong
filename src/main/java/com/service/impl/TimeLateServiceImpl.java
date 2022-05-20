package com.service.impl;

import com.Util.RequestStatusUtil;
import com.Util.SecurityUtil;
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

    @Override
    public Page<TimeLate> findAll(Pageable page) {
        return timeLateRepository.findAll(page);
    }

    @Override
    public TimeLate findById(Long id) {
        return timeLateRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

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

    @Override
    public TimeLate update(TimelateModel model) {
        TimeLate timeLate = findById(model.getId());
        timeLate.setId(model.getId());
        timeLate.setStatus(model.getStatus());
        timeLateRepository.save(timeLate);
        return timeLate;
    }

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

    @Override
    public boolean changeStatus(List<Long> ids, RequestStatusUtil status) {
        ids.forEach(id -> {
            TimeLate original = findById(id);
            original.setStatus(status.toString());
            this.timeLateRepository.save(original);
        });
        return true;
    }
}
