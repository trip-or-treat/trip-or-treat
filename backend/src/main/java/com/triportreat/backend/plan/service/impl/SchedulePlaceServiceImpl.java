package com.triportreat.backend.plan.service.impl;

import com.triportreat.backend.plan.error.exception.SchedulePlaceNotFoundException;
import com.triportreat.backend.plan.repository.SchedulePlaceRepository;
import com.triportreat.backend.plan.service.SchedulePlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SchedulePlaceServiceImpl implements SchedulePlaceService {

    private final SchedulePlaceRepository schedulePlaceRepository;

    @Override
    public void validateSchedulePlaceOwner(Long userId, Long id) {
        schedulePlaceRepository.findById(id)
                .orElseThrow(SchedulePlaceNotFoundException::new);
        schedulePlaceRepository.findByIdAndUserId(id, userId)
                .orElseThrow(AuthenticateFailException::new);
    }

    @Transactional
    @Override
    public void deleteSchedulePlace(Long id) {
        schedulePlaceRepository.deleteById(id);
    }
}
