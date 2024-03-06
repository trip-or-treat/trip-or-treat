package com.triportreat.backend.place.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triportreat.backend.place.error.handler.exception.ApiCallFailedException;
import com.triportreat.backend.place.error.handler.exception.ApiResponseParseException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class ExternalApiService {
    private final RestTemplate restTemplate;
    private final RetryTemplate retryTemplate;

    @Value("${openapi.overviewUrl}")
    private String overviewUrl;

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

}
