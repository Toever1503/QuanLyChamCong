package com.service;

import com.Util.RequestStatusUtil;
import com.entity.OT;
import com.entity.OtModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OTService extends IBaseService<OT, OtModel, Long> {

    boolean changeStatus(List<Long> ids, RequestStatusUtil status);

    Page<OT> findAllRequestForManager(Long id, Pageable page);
}
