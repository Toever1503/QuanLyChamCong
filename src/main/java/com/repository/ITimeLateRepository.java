package com.repository;

import com.entity.TimeLate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITimeLateRepository extends JpaRepository<TimeLate, Long> {
    Page<TimeLate> findAll(Pageable pageable);
}
