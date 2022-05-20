package com.web;

import com.Util.SecurityUtil;
import com.dto.ResponseDto;
import com.entity.Position;
import com.repository.IPositionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("api/positions")
public class PositionResources {

    private final IPositionRepository positionRepository;

    public PositionResources(IPositionRepository positionRepository) {
        this.positionRepository = positionRepository;
        try {
            this.positionRepository.saveAll(List.of(new Position(1l, Position.ADMINISTRATOR),
                    new Position(2l, Position.HR_MANAGER),
                    new Position(3l, Position.LEADER),
                    new Position(4l, Position.STAFF)));

        } catch (Exception e) {
            System.out.println("Position already exists");
        }

    }

    @Transactional
    @GetMapping("allowed-position")
    public ResponseDto getAll() {
        return ResponseDto.of(this.positionRepository.findAllPositionAllowedByManager(SecurityUtil.getCurrentUserId()), "get all positions");
    }
}
