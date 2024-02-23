package com.triportreat.backend.scheduler;

import com.triportreat.backend.scheduler.dto.OpenApiPlaceResponseDto;
import com.triportreat.backend.scheduler.dto.OpenApiPlaceResponseDto.Item;
import com.triportreat.backend.scheduler.service.OpenApiService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CronTable {

    private final OpenApiService openApiService;
    private final RestTemplate restTemplate;

    @Value("${schedule.use}")
    private boolean isRunnable;

    @Value("${openapi.url}")
    private String baseUrl;

    @Value("${openapi.key}")
    private String serviceKey;

    @Scheduled(cron = "${schedule.cron}")
    public void updatePlaceData() {
        if (isRunnable) {
            Integer totalCount = getTotalCount();

            List<Item> items = getAllPlaces(totalCount);
            openApiService.updatePlace(items);
        }
    }

    private List<Item> getAllPlaces(Integer totalCount) {
        URI requestURl = setRequestUrl(totalCount, 1);
        ResponseEntity<OpenApiPlaceResponseDto> response = restTemplate.getForEntity(requestURl,
                OpenApiPlaceResponseDto.class);

        List<Item> items = response.getBody().getResponse().getBody().getItems().getItem();
        return items;
    }

    private Integer getTotalCount() {
        URI requestUrl = setRequestUrl(1, 1);
        ResponseEntity<OpenApiPlaceResponseDto> response = restTemplate.getForEntity(requestUrl,
                OpenApiPlaceResponseDto.class);

        return response.getBody().getResponse().getBody().getTotalCount();
    }

    private URI setRequestUrl(Integer numOfRows, Integer requestPage) {
        return URI.create(baseUrl + "&serviceKey=" + serviceKey + "&numOfRows=" + numOfRows + "&pageNo=" + requestPage);
    }
}