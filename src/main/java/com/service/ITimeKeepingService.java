package com.service;

import com.entity.TimeKeeping;
import com.model.RequestStatusUtil;
import com.model.TimeKeepingModel;

public interface ITimeKeepingService extends IBaseService<TimeKeeping, TimeKeepingModel, Long> {
    TimeKeeping changeStatus(Long id, RequestStatusUtil status);
}
