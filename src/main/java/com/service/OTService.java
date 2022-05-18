package com.service;

import com.Util.RequestStatusUtil;
import com.entity.OT;
import com.entity.OtModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OTService extends IBaseService<OT, OtModel, Long> {

    OT changeStatus(Long id, RequestStatusUtil status);

    Page<OT> findAllRequestForManager(Long id, Pageable page);
}
