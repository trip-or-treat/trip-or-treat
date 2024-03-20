package com.triportreat.backend.plan.service;

public interface SchedulePlaceService {

    void validateSchedulePlaceOwner(Long userId, Long id);

    void deleteSchedulePlace(Long id);
}
