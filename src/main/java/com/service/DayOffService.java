package com.service;

import com.Util.RequestStatusUtil;
import com.dto.DayOffDTO;
import com.entity.DayOff;

public interface DayOffService extends IBaseService<DayOff, DayOffDTO, Long>{
    DayOff changeStatus(Long id, RequestStatusUtil status);
}
