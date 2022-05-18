package com.service;

import com.Util.RequestStatusUtil;
import com.dto.DayOffDTO;
import com.entity.DayOff;
import com.model.DayOffModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DayOffService extends IBaseService<DayOff, DayOffModel, Long>{
    DayOff changeStatus(Long id, RequestStatusUtil status);

    Page<DayOff> findAllDayOffByEmployeeId(Long employeeId, Pageable page);

    Page<DayOff> findAllRequestForManager(Long id, Pageable page);
}
