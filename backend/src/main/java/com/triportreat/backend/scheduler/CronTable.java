package com.triportreat.backend.scheduler;

import com.triportreat.backend.scheduler.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CronTable {

    private final OpenApiService openApiService;

    @Value("${schedule.use}")
    private boolean isRunnable;

    @Scheduled(cron = "${schedule.cron}")
    public void updatePlaceData() {
        if (isRunnable) {
            openApiService.updatePlaceData();
        }
    }
}
