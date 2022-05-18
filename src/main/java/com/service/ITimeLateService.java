package com.service;

import com.Util.RequestStatusUtil;
import com.entity.TimeKeeping;
import com.entity.TimeLate;
import com.model.TimelateModel;

public interface ITimeLateService extends IBaseService<TimeLate, TimelateModel, Long> {
    TimeLate changeStatus(Long id, RequestStatusUtil status);
}
