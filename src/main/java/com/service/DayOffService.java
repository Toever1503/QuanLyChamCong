package com.service;

import com.Util.RequestStatusUtil;
import com.entity.DayOff;
import com.model.DayOffModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DayOffService extends IBaseService<DayOff, DayOffModel, Long>{
    boolean changeStatus(List<Long> ids, RequestStatusUtil status);

    Page<DayOff> findAllRequestForManager(Long id, Pageable page);

    Page<DayOff> getAllRequestsByTime(long timein ,long timeout,  Pageable page);

    Page<DayOff> findAllStaffRequests(Long staffId, Pageable page);
}
