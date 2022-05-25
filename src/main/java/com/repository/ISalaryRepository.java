package com.repository;

import com.entity.Salary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISalaryRepository extends JpaRepository<Salary, Long> {
    Page<Salary> findAllByStaffStaffIdInAndMonth(List<Long> staffIds, int month, Pageable pageable);

    List<Salary> findAllByStaffStaffIdInAndMonth(List<Long> staffIds, int month);

    Optional<Salary> findByStaffStaffIdAndMonth(Long staffId, int month);

    void deleteAllByStaffStaffId(Long staffId);
    Optional<Salary> findTopByStaffStaffIdOrderByIdDesc(Long staffId);
}
