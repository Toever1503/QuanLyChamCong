package com.service.impl;

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
        if(dayOffRepository.findById(id).isPresent())
            return dayOffRepository.findById(id).get();
        else
            return null;
    }

    @Override
    public DayOff add(DayOffDTO model) {
        DayOff savedOT = null;
        if (model != null) {
            DayOff otEntity = new DayOff();
            if(otEntity.getStaff()!=null){
                if(staffRepository.findById(model.getStaff_id()).isPresent())
                    otEntity.setStaff(staffRepository.findById(model.getStaff_id()).get());
                else {
                    otEntity.setStaff(null);
                }
            }
            else
                otEntity.setStaff(null);
            otEntity.setStatus(model.getStatus());
            otEntity.setTime_start(model.getTime_start());
            otEntity.setTime_end(model.getTime_end());
            savedOT = dayOffRepository.save(otEntity);
        }
        else {
            return null;
        }
        return savedOT;
    }

    @Override
    public List<DayOff> add(List<DayOffDTO> model) {
        List<DayOff> savedOTs = new ArrayList<>();
        for (DayOffDTO dto: model
        ) {
            DayOff otEntity = new DayOff();
            otEntity.setStaff(staffRepository.findById(dto.getId()).get());
            otEntity.setStatus(dto.getStatus());
            otEntity.setTime_start(dto.getTime_start());
            otEntity.setTime_end(dto.getTime_end());
            savedOTs.add(dayOffRepository.save(otEntity));
        }
        return savedOTs;
    }

    @Override
    public DayOff update(DayOffDTO model) {
        DayOff savedOT = null;
        if (model != null) {
            if(dayOffRepository.findById(model.getId()).isPresent()){
                DayOff otEntity = dayOffRepository.findById(model.getId()).get();
                if(otEntity.getStaff() != null){
                    if (staffRepository.findById(model.getStaff_id()).isPresent())
                        otEntity.setStaff(staffRepository.findById(model.getStaff_id()).get());
                }else
                    otEntity.setStaff(null);
                if(model.getTime_start()!=null)
                    otEntity.setTime_start(model.getTime_start());
                if (model.getStatus()!=null)
                    otEntity.setStatus(model.getStatus());
                if (model.getTime_end()!=null)
                    otEntity.setTime_end(model.getTime_end());
                savedOT = dayOffRepository.save(otEntity);
            }
        }else {
            return null;
        }
        return savedOT;
    }

    @Override
    public boolean deleteById(Long id) {
        if(dayOffRepository.findById(id).isPresent()){
            dayOffRepository.delete(dayOffRepository.findById(id).get());
            return true;
        }else
            return false;
    }

    @Override
    public boolean deleteByIds(List<Long> id) {
        for (Long i: id
        ) {
            if(dayOffRepository.findById(i).isPresent()) {
                dayOffRepository.delete(dayOffRepository.findById(i).get());
            }else
                return false;
        }
        return true;
    }
}
