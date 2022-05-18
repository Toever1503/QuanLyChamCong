package com.web;

import com.dto.ResponseDto;
import com.repository.IPositionRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/positions")
public class PositionResources {

    private final IPositionRepository positionRepository;

    public PositionResources(IPositionRepository positionRepository) {
        this.positionRepository = positionRepository;
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
