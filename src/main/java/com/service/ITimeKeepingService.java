package com.service;

import com.entity.TimeKeeping;
import com.Util.RequestStatusUtil;
import com.model.TimeKeepingModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ITimeKeepingService extends IBaseService<TimeKeeping, TimeKeepingModel, Long> {
    boolean changeStatus(List<Long> ids, RequestStatusUtil status);

    Page<TimeKeeping> getAllRequestsByDate(long date, Pageable page);
}
