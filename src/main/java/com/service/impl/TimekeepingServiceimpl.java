package com.service.impl;

import com.Util.SecurityUtil;
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
        return timekeepingRepository.findAll();
    }

    @Override
    public Page<TimeKeeping> findAll(Pageable page) {
        return timekeepingRepository.findAll(page);
    }

    @Override
    public TimeKeeping findById(Long id) {
        return timekeepingRepository.findById(id).orElseThrow((() -> new RuntimeException("Not found")));
    }

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

    @Override
    public boolean deleteById(Long id) {
        timekeepingRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean deleteByIds(List<Long> id) {
        return false;
    }

    @Override
    public boolean changeStatus(List<Long> ids, RequestStatusUtil status) {
        ids.forEach(id -> {
            TimeKeeping original = findById(id);
            original.setStatus(status.toString());
            this.timekeepingRepository.save(original);
        });
        return true;
    }
}
