package com.web;

import com.dto.ResponseDto;
import com.entity.Position;
import com.repository.IPositionRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    public ResponseDto getAll(Pageable page) {
        return ResponseDto.of(this.positionRepository.findAll(page), "get all positions");
    }

    @GetMapping("allowed-position")
    public ResponseDto getAll(@RequestParam Long id) {
        return ResponseDto.of(this.positionRepository.findAllPosition(id), "get all positions");
    }
}
