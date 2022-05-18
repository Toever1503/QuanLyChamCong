package com.repository;

import com.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPositionRepository extends JpaRepository<Position, Long> {
    Position findByPositionName(String name);
}
