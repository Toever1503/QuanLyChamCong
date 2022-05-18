package com.service;

import com.Util.RequestStatusUtil;
import com.dto.OTDto;
import com.entity.OT;

public interface OTService extends IBaseService<OT,OTDto,Long>{

    OT changeStatus(Long id, RequestStatusUtil status);
}
