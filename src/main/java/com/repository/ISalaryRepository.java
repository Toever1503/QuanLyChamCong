package com.repository;

import com.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISalaryRepository extends JpaRepository<Salary, Long> {
}
