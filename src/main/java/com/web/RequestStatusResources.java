package com.web;

import com.dto.ResponseDto;
import com.Util.RequestStatusUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/request-status")
public class RequestStatusResources {


    @GetMapping
    public ResponseDto getAll() {
        return ResponseDto.of(RequestStatusUtil.values(), "Get Request Status");
    }
}
