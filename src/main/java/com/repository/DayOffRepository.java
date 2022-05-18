package com.repository;

import com.entity.DayOff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayOffRepository extends JpaRepository<DayOff, Long> {
    Page<DayOff> findAllByStaffStaffId(Long staffId, Pageable pageable);
}
