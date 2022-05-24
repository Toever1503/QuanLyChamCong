package com.repository;

import com.entity.OT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IOTRepository extends JpaRepository<OT, Long> {

    //Tìm kiếm tất cả yêu cầu làm thêm giờ từ nhân viên theo quản lí // Find all overtime request from employee by manager id
    @Query("select o from OT o join Staff s on s.staffId = o.staff.staffId where s.manager.staffId = ?1")
    Page<OT> findAllRequestForManager(Long managerId, Pageable page);

    Page<OT> findAllByStaffStaffId(Long staffId, Pageable page);
    //Tìm kiếm tất cả yêu cầu làm thêm giờ từ nhân viên theo quản lí, thời gian nghỉ // Find all overtime request from employee by manager id and time
    @Query("select o from OT o join Staff s on s.staffId = o.staff.staffId where s.manager.staffId = ?1 and o.time_start >= ?2 and o.time_start <= ?3")
    Page<OT> findAllRequestByDate(Long staffId, Long begin, Long last, Pageable page);

    void deleteAllByStaffStaffId(Long staff_id);
}
