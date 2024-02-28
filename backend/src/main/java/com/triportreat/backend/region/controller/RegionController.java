package com.triportreat.backend.region.controller;

import com.triportreat.backend.common.response.ResponseResult;
import com.triportreat.backend.region.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.triportreat.backend.common.response.SuccessMessage.GET_SUCCESS;

@RestController
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping("/regions")
    public ResponseEntity<?> getRegions() {
        return ResponseEntity.ok().body(ResponseResult.success(GET_SUCCESS.getMessage(), regionService.getRegions()));
    }
}
