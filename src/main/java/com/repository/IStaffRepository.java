package com.repository;

import com.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface IStaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByEmail(String email);

    //Tìm tất cả nhân viên của quản lí theo mã quản lí // Find all staff by manager id
    @Query("select s from Staff s where s.manager.staffId = ?1")
    Page<Staff> findAllStaffForManager(Long id ,Pageable pageable);

    @Query("select s from Staff s where s.manager.staffId = ?1")
    List<Staff> findAllStaffForManager(Long id);

}
