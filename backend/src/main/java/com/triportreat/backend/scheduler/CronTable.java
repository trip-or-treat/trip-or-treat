package com.triportreat.backend.scheduler;

import com.triportreat.backend.common.cache.RedisService;
import com.triportreat.backend.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CronTable {

    private final RedisService redisService;
    private final PlaceService placeService;

    @Value("${schedule.use}")
    private boolean isRunnable;

    @Scheduled(cron = "${schedule.place-data.cron}")
    public void updatePlacesByExternalApi() {
        if (isRunnable) {
            placeService.updatePlacesByExternalApi();
            log.info("Place 데이터 업데이트 종료");
        }
    }

    @Scheduled(cron = "${schedule.place-views.cron}")
    public void updatePlaceViewCounts() {
        if (isRunnable) {
            redisService.updatePlaceViewCounts();
            log.info("Place 조회수 업데이트 종료");
        }
    }
}
