package com.repository;

import com.entity.DayOff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IDayOffRepository extends JpaRepository<DayOff, Long> {
    Page<DayOff> findAllByStaffStaffId(Long staffId, Pageable pageable);

    //Tìm kiếm tất cả yêu cầu nghỉ từ nhân viên theo quản lí // Find all day off request from employee by manager id
    @Query("select d from DayOff d join Staff s on s.staffId = d.staff.staffId where s.manager.staffId = ?1")
    Page<DayOff> findAllRequestForManager(Long managerId, Pageable page);

    //Tìm kiếm tất cả yêu cầu nghỉ từ nhân viên theo quản lí, thời gian nghỉ // Find all day off request from employee by manager id and time
    @Query("select d from DayOff d join Staff s on s.staffId = d.staff.staffId where s.manager.staffId = ?1 and d.time_start >= ?2 and d.time_start <= ?3")
    Page<DayOff> findAllRequestByDate(Long staffId, Long begin, Long last, Pageable page);

    void deleteAllByStaffStaffId(Long staff_id);
}
