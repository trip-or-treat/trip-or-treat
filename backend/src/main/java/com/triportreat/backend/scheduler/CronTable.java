package com.triportreat.backend.scheduler;

import com.triportreat.backend.batch.BatchJobService;
import com.triportreat.backend.common.cache.RedisService;
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
    private final BatchJobService batchJobService;

    @Value("${schedule.use}")
    private boolean isRunnable;

    @Scheduled(cron = "${schedule.place-data.cron}")
    public void updatePlacesByExternalApi() {
        if (isRunnable) {
            log.info("장소데이터 업데이트 시작");
            batchJobService.runPlaceUpdateJob();
            log.info("장소데이터 업데이트 종료");
        }
    }

    @Scheduled(cron = "${schedule.place-views.cron}")
    public void updatePlaceViewCounts() {
        if (isRunnable) {
            log.info("장소 조회수 업데이트 시작");
            redisService.updatePlaceViewCounts();
            log.info("장소 조회수 업데이트 종료");
        }
    }
}
