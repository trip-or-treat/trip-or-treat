package com.triportreat.backend.place.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triportreat.backend.common.response.FailMessage;
import com.triportreat.backend.place.domain.TourApiPlaceResponseDto;
import com.triportreat.backend.place.domain.TourApiPlaceResponseDto.Item;
import com.triportreat.backend.place.error.handler.exception.ApiCallFailedException;
import com.triportreat.backend.place.error.handler.exception.ApiResponseParseException;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalApiService {
    private final RestTemplate restTemplate;
    private final RetryTemplate retryTemplate;

    @Value("${openapi.overviewUrl}")
    private String overviewUrl;

    @Value("${openapi.place-data-url}")
    private String placeDataUrl;

    @Value("${openapi.key}")
    private String serviceKey;

    public String callExternalApiForOverView(Long id) {
        URI url = URI.create(overviewUrl + "&serviceKey=" + serviceKey + "&contentId=" + id + "&overviewYN=Y");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response;
        try {
            response = retryTemplate.execute(context -> restTemplate.exchange(url, HttpMethod.GET, entity, String.class));
        } catch (RestClientException e) {
            throw new ApiCallFailedException();
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new ApiResponseParseException();
        }
        String overview = root.path("response").path("body").path("items").path("item").get(0).path("overview").asText();

        if (overview == null || overview.isEmpty()) {
            throw new ApiResponseParseException();
        }

        return overview;
    }

    public List<Item> callExternalApiForUpdatePlaces() {
        try {
            Integer totalCount = getTotalCount();
            return getPlaces(totalCount);
        } catch (RestClientException e) {
            log.error(FailMessage.API_CALL_FAILED.getMessage());
            throw new ApiCallFailedException();
        } catch (NullPointerException e) {
            log.error(FailMessage.API_RESPONSE_PARSE_FAILED.getMessage());
            throw new ApiResponseParseException();
        }
    }

    private List<Item> getPlaces(Integer totalCount) {
        URI requestURl = setRequestUrl(totalCount);
        ResponseEntity<TourApiPlaceResponseDto> response = restTemplate.getForEntity(requestURl,
                TourApiPlaceResponseDto.class);

        return response.getBody().getResponse().getBody().getItems().getItem();
    }

    private Integer getTotalCount() {
        URI requestUrl = setRequestUrl(1);
        ResponseEntity<TourApiPlaceResponseDto> response = restTemplate.getForEntity(requestUrl,
                TourApiPlaceResponseDto.class);

        return response.getBody().getResponse().getBody().getTotalCount();
    }

    private URI setRequestUrl(Integer numOfRows) {
        return URI.create(placeDataUrl + "&serviceKey=" + serviceKey + "&numOfRows=" + numOfRows + "&pageNo=" + 1);
    }
}
