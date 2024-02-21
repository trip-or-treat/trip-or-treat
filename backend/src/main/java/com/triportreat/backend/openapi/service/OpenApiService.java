package com.triportreat.backend.openapi.service;

import com.triportreat.backend.openapi.dto.PlaceDataResponseDto;
import com.triportreat.backend.openapi.dto.PlaceDataResponseDto.Item;
import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.repository.PlaceRepository;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional
public class OpenApiService {

    private final PlaceRepository placeRepository;
    private final RestTemplate restTemplate;

    @Value("${openapi.url}")
    private String baseUrl;

    @Value("${openapi.key}")
    private String serviceKey;

    @Value("${openapi.rows}")
    private int numOfRows;

    public void storeInitPlaceData() {
        int totalPages = getTotalPages();

        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            URI baseUrl = createUri(numOfRows, currentPage);
            ResponseEntity<PlaceDataResponseDto> responseEntity = restTemplate.getForEntity(baseUrl, PlaceDataResponseDto.class);

            List<Item> responseItems = responseEntity.getBody().getResponse().getBody().getItems().getItem();
            List<Place> places = new ArrayList<>();

            for (Item item : responseItems) {
                //15는 축제공연행사, 25는 여행코스이므로 제외
                if (item.getContentTypeId() == 15 || item.getContentTypeId() == 25) {
                    continue;
                }
                Place place = getPlace(item);
                places.add(place);
            }
            placeRepository.saveAllAndFlush(places);
        }
    }

    public Place getPlace(Item item) {
        Place place = Place.builder()
                .id(item.getId())
                .address(item.getAddress())
                .addressDetail(item.getAddressDetail())
                .name(item.getName())
                .mainCategoryId(item.getMainCategoryId())
                .midCategoryId(item.getMidCategoryId())
                .imageOrigin(item.getImageOrigin())
                .imageThumbnail(item.getImageThumbnail())
                .latitude(item.getLatitude())
                .longitude(item.getLongitude())
                .sigunguCode(item.getSigunguCode())
                .build();
        return place;
    }

    public int getTotalPages() {

        URI probeUrl = createUri(1, 1);
        ResponseEntity<PlaceDataResponseDto> responseEntity = restTemplate.getForEntity(probeUrl, PlaceDataResponseDto.class);

        int totalCount = responseEntity.getBody().getResponse().getBody().getTotalCount();
        return (int) Math.ceil((double) totalCount / numOfRows);
    }

    public URI createUri(int numOfRows, int pageNo) {
        return URI.create(baseUrl + "&serviceKey=" + serviceKey + "&numOfRows=" + numOfRows + "&pageNo=" + pageNo);
    }

    public void setPlaceTest() {
        URI uri = createUri(10, 1);
        ResponseEntity<PlaceDataResponseDto> responseEntity = restTemplate.getForEntity(uri, PlaceDataResponseDto.class);
        List<Item> responseItems = responseEntity.getBody().getResponse().getBody().getItems().getItem();
        List<Place> places = new ArrayList<>();
        for (Item item : responseItems) {
            Place place = Place.builder()
                    .id(item.getId())
                    .address(item.getAddress())
                    .addressDetail(item.getAddressDetail())
                    .name(item.getName())
                    .mainCategoryId(item.getMainCategoryId())
                    .midCategoryId(item.getMidCategoryId())
                    .imageOrigin(item.getImageOrigin())
                    .imageThumbnail(item.getImageThumbnail())
                    .latitude(item.getLatitude())
                    .longitude(item.getLongitude())
                    .sigunguCode(item.getSigunguCode())
                    .build();
            places.add(place);
        }
        placeRepository.saveAll(places);
    }
}
