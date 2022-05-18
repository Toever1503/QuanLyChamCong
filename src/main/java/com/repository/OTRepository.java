package com.repository;

import com.entity.OT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTRepository extends JpaRepository<OT, Long> {
}
