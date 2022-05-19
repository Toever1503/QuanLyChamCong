package com.repository;

import com.entity.TimeKeeping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITimekeepingRepository extends JpaRepository<TimeKeeping, Long> {
    @Override
    Page<TimeKeeping> findAll(Pageable pageable);
}
