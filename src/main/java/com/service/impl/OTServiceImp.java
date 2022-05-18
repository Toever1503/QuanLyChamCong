package com.service.impl;

import com.Util.RequestStatusUtil;
import com.dto.OTDto;
import com.entity.OT;
import com.repository.OTRepository;
import com.repository.StaffRepository;
import com.service.OTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OTServiceImp implements OTService {
    @Autowired
    OTRepository otRepository;
    @Autowired
    StaffRepository staffRepository;

    @Override
    public List<OT> findAll() {
        return otRepository.findAll();
    }

    @Override
    public Page<OT> findAll(Pageable page) {
        return otRepository.findAll(page);
    }

    @Override
    public OT findById(Long id) {
        return otRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Override
    public OT add(OTDto model) {
        OT savedOT = null;
        if (model != null) {
            OT otEntity = new OT();
            if (otEntity.getStaff() != null) {
                if (staffRepository.findById(model.getStaff_id()).isPresent())
                    otEntity.setStaff(staffRepository.findById(model.getStaff_id()).get());
                else {
                    otEntity.setStaff(null);
                }
            } else
                otEntity.setStaff(null);
            otEntity.setStatus(RequestStatusUtil.PENDING.name());
            otEntity.setMultiply(model.getMultiply());
            otEntity.setTime_start(model.getTime_start());
            otEntity.setTime_end(model.getTime_end());
            savedOT = otRepository.save(otEntity);
        } else {
            return null;
        }
        return savedOT;
    }

    @Override
    public List<OT> add(List<OTDto> model) {
        List<OT> savedOTs = new ArrayList<>();
        for (OTDto ot : model
        ) {
            OT otEntity = new OT();
            otEntity.setStaff(staffRepository.findById(ot.getId()).get());
            otEntity.setStatus(ot.getStatus());
            otEntity.setTime_start(ot.getTime_start());
            otEntity.setTime_end(ot.getTime_end());
            otEntity.setMultiply(ot.getMultiply());
            savedOTs.add(otRepository.save(otEntity));
        }
        return savedOTs;
    }

    @Override
    public OT update(OTDto model) {
        OT savedOT = null;
        if (model != null) {
            if (otRepository.findById(model.getId()).isPresent()) {
                OT otEntity = otRepository.findById(model.getId()).get();
                if (otEntity.getStaff() != null) {
                    if (staffRepository.findById(model.getStaff_id()).isPresent())
                        otEntity.setStaff(staffRepository.findById(model.getStaff_id()).get());
                } else
                    otEntity.setStaff(null);
                if (model.getTime_start() != null)
                    otEntity.setTime_start(model.getTime_start());
                if (model.getStatus() != null)
                    otEntity.setStatus(model.getStatus());
                if (model.getTime_end() != null)
                    otEntity.setTime_end(model.getTime_end());
                if (model.getMultiply() != null)
                    otEntity.setMultiply(model.getMultiply());
                savedOT = otRepository.save(otEntity);
            }
        } else {
            return null;
        }
        return savedOT;
    }

    @Override
    public boolean deleteById(Long id) {
        if (otRepository.findById(id).isPresent()) {
            otRepository.delete(otRepository.findById(id).get());
            return true;
        } else
            return false;
    }

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

    @Override
    public OT changeStatus(Long id, RequestStatusUtil status) {
        OT original = this.findById(id);
        original.setStatus(status.name());
        return this.otRepository.save(original);
    }
}
