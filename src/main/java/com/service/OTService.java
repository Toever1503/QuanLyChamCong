package com.service;

import com.Util.RequestStatusUtil;
import com.entity.OT;
import com.model.OtModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OTService extends IBaseService<OT, OtModel, Long> {

    boolean changeStatus(List<Long> ids, RequestStatusUtil status);

    Page<OT> findAllRequestForManager(Long id, Pageable page);

    Page<OT>  getAllRequestsByTime(long timein, long timeout, Pageable page);

    Page<OT> findAllStaffRequests(Long staffId,Pageable page);
}
