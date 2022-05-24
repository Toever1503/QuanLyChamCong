package com.repository;

import com.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPositionRepository extends JpaRepository<Position, Long> {
    Position findByPositionName(String name);
    //Tìm tất cả vị trí được cho phép bởi quản lí theo id nhân viên // Find all allowed position by manager using staff id
    @Query("select p from Position p where p.positionId > (select s.position.positionId from Staff s where s.staffId = ?1)")
    List<Position> findAllPositionAllowedByManager(Long staffId);
}
