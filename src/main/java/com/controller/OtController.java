package com.controller;

import com.dto.DayOffDTO;
import com.dto.OTDto;
import com.dto.ResponseDto;
import com.service.OTService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ot")
public class OtController {
    @Autowired
    OTService otService;

    @Transactional
    @GetMapping(produces = "application/json",value ="/all")
    public ResponseDto getAllOtReq(Pageable pageable){
        List<OTDto> dtoList = otService.findAll(pageable).stream().map(ot -> OTDto.builder().id(ot.getId()).multiply(ot.getMultiply()).status(ot.getStatus()).time_start(ot.getTime_start()).time_end(ot.getTime_end()).time_created(ot.getTime_created()).build()).collect(Collectors.toList());
        return  ResponseDto.of(dtoList,"GET");
    }
    @Transactional
    @GetMapping(produces = "application/json",value ="/{manager_id}/all")
    public ResponseDto getAllOtReqForMng(@PathVariable("manager_id") Long idm, Pageable pageable){
        List<OTDto> dtoList = otService.findAll(pageable).stream().filter(ot -> ot.getStaff().getStaffId() == idm).map(ot -> OTDto.builder().id(ot.getId()).status(ot.getStatus()).time_start(ot.getTime_start()).time_end(ot.getTime_end()).time_created(ot.getTime_created()).build()).collect(Collectors.toList());

        return ResponseDto.of(dtoList,"GET");
    }

    @Transactional
    @PostMapping(produces = "application/json",value ="/add")
    public ResponseDto addNewOT(@RequestBody OTDto otDto){
        if(otDto !=null){
            return ResponseDto.of(otService.add(otDto),"POST");
        }else
            return ResponseDto.of(null,"POST");
    }

    @Transactional
    @PutMapping(produces = "application/json",value ="/edit/{id}")
    public ResponseDto editOT(@RequestBody OTDto otDto, @PathVariable("id") Long id){
        if(otDto !=null){
            otDto.setId(id);
            return ResponseDto.of(otService.update(otDto),"POST");
        }else
            return ResponseDto.of(null,"POST");
    }

    @Transactional
    @DeleteMapping(produces = "application/json",value ="/delete/{id}")
    public ResponseDto delOt(@PathVariable("id") Long id){
        return ResponseDto.of(otService.deleteById(id),"Delete");
    }

}
