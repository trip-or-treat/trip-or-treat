package com.triportreat.backend.plan.controller;

import static com.triportreat.backend.common.response.SuccessMessage.DELETE_SUCCESS;

import com.triportreat.backend.auth.utils.Auth;
import com.triportreat.backend.common.response.ResponseResult;
import com.triportreat.backend.plan.service.SchedulePlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule-place")
@RequiredArgsConstructor
public class SchedulePlaceController {

    private final SchedulePlaceService schedulePlaceService;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedulePlace(@Auth Long userId,
                                                 @PathVariable("id") Long id) {
        schedulePlaceService.validateSchedulePlaceOwner(userId, id);

        schedulePlaceService.deleteSchedulePlace(id);
        return ResponseEntity.ok().body(ResponseResult.success(DELETE_SUCCESS.getMessage(), null));
    }
}
