package com.service;

import com.entity.TimeKeeping;
import com.Util.RequestStatusUtil;
import com.model.TimeKeepingModel;

import java.util.List;

public interface ITimeKeepingService extends IBaseService<TimeKeeping, TimeKeepingModel, Long> {
    boolean changeStatus(List<Long> ids, RequestStatusUtil status);
}
