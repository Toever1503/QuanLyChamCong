package com.repository;

import com.entity.TimeLate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ITimeLateRepository extends JpaRepository<TimeLate, Long> {
    Page<TimeLate> findAll(Pageable pageable);

    @Query("select t from TimeLate t join Staff s on s.staffId = t.staff.staffId where s.manager.staffId = ?1 and t.timeIn >= ?2 and t.timeIn <= ?3")
    Page<TimeLate> findAllRequestByDate(Long staffId, Long begin, Long last, Pageable page);

    @Query("select t from TimeLate t join Staff s on s.staffId = t.staff.staffId where s.manager.staffId = ?1")
    Page<TimeLate> findStaffOfManager(Long staffId, Pageable page);
}
