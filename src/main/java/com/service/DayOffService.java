package com.service;

import com.Util.RequestStatusUtil;
import com.dto.DayOffDTO;
import com.entity.DayOff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DayOffService extends IBaseService<DayOff, DayOffDTO, Long>{
    DayOff changeStatus(Long id, RequestStatusUtil status);

    Page<DayOff> findAllDayOffByEmployeeId(Long employeeId, Pageable page);
}
