package com.service.impl;

import com.entity.TimeKeeping;
import com.model.TimeKeepingModel;
import com.repository.ITimekeepingRepository;
import com.service.ITimeKeepingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimekeepingServiceimpl implements ITimeKeepingService {
    private final ITimekeepingRepository timekeepingRepository;

    public TimekeepingServiceimpl(ITimekeepingRepository timekeepingRepository) {
        this.timekeepingRepository = timekeepingRepository;
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
        return timekeepingRepository.save(timeKeeping);
    }

    @Override
    public List<TimeKeeping> add(List<TimeKeepingModel> model) {
        return null;
    }

    @Override
    public TimeKeeping update(TimeKeepingModel model) {
        TimeKeeping timeKeeping = findById(model.getId());
        timeKeeping.setId(model.getId());
//        timeKeeping.setDate(model.getDate());
//        timeKeeping.setTimeIn(model.getTimeIn());
//        timeKeeping.setTimeOut(model.getTimeOut());
//        timeKeeping.setNote(model.getNote());
        timeKeeping.setStatus(model.getStatus().toString());
        timekeepingRepository.save(timeKeeping);
        return timeKeeping;
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            timekeepingRepository.deleteById(id);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByIds(List<Long> id) {
        return false;
    }
}