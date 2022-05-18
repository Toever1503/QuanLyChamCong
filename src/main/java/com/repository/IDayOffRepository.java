package com.repository;

import com.entity.DayOff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IDayOffRepository extends JpaRepository<DayOff, Long> {
    Page<DayOff> findAllByStaffStaffId(Long staffId, Pageable pageable);

    @Query("select d from DayOff d join Staff s on s.staffId = d.staff.staffId where s.manager.staffId = ?1")
    Page<DayOff> findAllRequestForManager(Long managerId, Pageable page);
}
