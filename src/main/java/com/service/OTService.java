package com.service;

import com.Util.RequestStatusUtil;
import com.entity.OT;
import com.entity.OtModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OTService extends IBaseService<OT, OtModel, Long> {

    boolean changeStatus(List<Long> ids, RequestStatusUtil status);

    Page<OT> findAllRequestForManager(Long id, Pageable page);

    Page<OT>  getAllRequestsByDate(long date, Pageable page);
}
