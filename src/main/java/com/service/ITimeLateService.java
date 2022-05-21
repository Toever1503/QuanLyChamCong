package com.service;

import com.Util.RequestStatusUtil;
import com.entity.TimeKeeping;
import com.entity.TimeLate;
import com.model.TimelateModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ITimeLateService extends IBaseService<TimeLate, TimelateModel, Long> {
    boolean changeStatus(List<Long> ids, RequestStatusUtil status);

    Page<TimeLate> getAllRequestsByDate(long date, Pageable page);

    Page<TimeLate> findAllMyRequests(Pageable page);
}
