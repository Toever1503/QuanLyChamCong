package com.service;

import com.Util.RequestStatusUtil;
import com.entity.TimeKeeping;
import com.entity.TimeLate;
import com.model.TimelateModel;

import java.util.List;

public interface ITimeLateService extends IBaseService<TimeLate, TimelateModel, Long> {
    boolean changeStatus(List<Long> ids, RequestStatusUtil status);
}
