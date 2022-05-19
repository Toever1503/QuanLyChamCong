package com.repository;

import com.entity.OT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IOTRepository extends JpaRepository<OT, Long> {

    @Query("select o from OT o join Staff s on s.staffId = o.staff.staffId where s.manager.staffId = ?1")
    Page<OT> findAllRequestForManager(Long managerId, Pageable page);
}
