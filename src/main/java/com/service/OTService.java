package com.service;

import com.Util.RequestStatusUtil;
import com.entity.OT;
import com.entity.OtModel;

public interface OTService extends IBaseService<OT, OtModel, Long> {

    OT changeStatus(Long id, RequestStatusUtil status);
}
