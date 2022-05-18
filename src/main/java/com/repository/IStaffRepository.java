package com.repository;

import com.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IStaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByEmail(String email);

    @Query("select s from Staff as s JOIN TimeKeeping as t ON s.staffId = t.staff.staffId where s.manager.staffId = ?1")
    List<Staff> findStaffAndTimeKeep(Long id);

    @Query("select s from Staff as s JOIN TimeKeeping as t ON s.staffId = t.staff.staffId where s.manager.staffId = ?1")
    Page<Staff> findStaffPage(Long id ,Pageable pageable);
}
