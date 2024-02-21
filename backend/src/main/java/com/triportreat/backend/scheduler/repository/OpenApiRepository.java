package com.triportreat.backend.scheduler.repository;

import com.triportreat.backend.scheduler.dto.OpenApiPlaceResponse;
import jakarta.persistence.EntityManager;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Repository
@RequiredArgsConstructor
@Transactional
public class OpenApiRepository {

    private final RestTemplate restTemplate;
    private final EntityManager em;

    @Value("${openapi.url}")
    private String baseUrl;

    @Value("${openapi.key}")
    private String serviceKey;

    @Value("${openapi.rows}")
    private Integer numOfRows;

    public void updatePlaceData() {
        Integer currentPage = 1;
        Integer totalPages = getTotalPages(currentPage);

    }

    private Integer getTotalPages(Integer currentPage) {
        URI requestUrl = setRequestUrl(numOfRows, currentPage);
        ResponseEntity<OpenApiPlaceResponse> response = restTemplate.getForEntity(requestUrl,
                OpenApiPlaceResponse.class);

        Integer totalDataCount = response.getBody().getResponse().getBody().getTotalCount();
        return (totalDataCount / numOfRows) + 1;

    }

    private URI setRequestUrl(Integer numOfRows, Integer currentPage) {
        return URI.create(baseUrl + "&serviceKey=" + serviceKey + "&numOfRows=" + numOfRows + "&pageNo=" + currentPage);
    }
}
