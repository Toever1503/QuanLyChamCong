package com.service.impl;

import com.Util.RequestStatusUtil;
import com.dto.OTDto;
import com.entity.OT;
import com.entity.OtModel;
import com.repository.OTRepository;
import com.repository.StaffRepository;
import com.service.OTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
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

    OT toEntity(OtModel model){
        if(model == null) throw new RuntimeException("Ot Model is null");
        return OT.builder()
                .id(model.getId())
                .time_start(model.getTime_start())
                .time_end(model.getTime_end())
                .multiply(model.getMultiply())
                .status(model.getStatus())
                .build();
    }

    @Override
    public OT findById(Long id) {
        return otRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Override
    public OT add(OtModel model) {
        OT entity = toEntity(model);
        entity.setStaff(this.staffRepository.findById(model.getStaff_id()).orElseThrow(() -> new RuntimeException("Staff Not found")));
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

    @Override
    public Page<OT> findAllRequestForManager(Long id, Pageable page) {
        return this.otRepository.findAllRequestForManager(id, page);
    }
}
