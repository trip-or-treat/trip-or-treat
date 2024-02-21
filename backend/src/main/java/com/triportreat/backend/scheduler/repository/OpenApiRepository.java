package com.triportreat.backend.scheduler.repository;

import com.triportreat.backend.place.entity.ContentType;
import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.entity.SubCategory;
import com.triportreat.backend.scheduler.dto.OpenApiPlaceResponse;
import com.triportreat.backend.scheduler.dto.OpenApiPlaceResponse.Item;
import jakarta.persistence.EntityManager;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Repository
@RequiredArgsConstructor
public class OpenApiRepository {

    private final RestTemplate restTemplate;
    private final EntityManager em;

    @Value("${openapi.url}")
    private String baseUrl;

    @Value("${openapi.key}")
    private String serviceKey;

    @Value("${openapi.rows}")
    private Integer numOfRows;

    @Transactional
    public void updatePlaceData() {
        Integer totalPages = getTotalPages();

        updatePlacesToDatabase(totalPages);
    }

    private void updatePlacesToDatabase(Integer totalPages) {
        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            URI requestUrl = setRequestUrl(numOfRows, currentPage);
            ResponseEntity<OpenApiPlaceResponse> response = restTemplate.getForEntity(requestUrl,
                    OpenApiPlaceResponse.class);

            List<Item> responseItems = response.getBody().getResponse().getBody().getItems().getItem();
            for (Item item : responseItems) {
//                Region region = em.find(Region.class, item.getRegionId());

                ContentType contentType = em.find(ContentType.class, item.getContentTypeId());

                SubCategory subCategory = em.find(SubCategory.class, item.getSubCategoryId());

                Place place = Place.builder()
                        .id(item.getId())
//                        .region(region)
                        .contentType(contentType)
                        .subCategory(subCategory)
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
                        .views(0L)
                        .build();
                em.persist(place);
            }
        }
    }

    private Integer getTotalPages() {
        URI requestUrl = setRequestUrl(numOfRows, 1);
        ResponseEntity<OpenApiPlaceResponse> response = restTemplate.getForEntity(requestUrl,
                OpenApiPlaceResponse.class);

        Integer totalDataCount = response.getBody().getResponse().getBody().getTotalCount();
        return (totalDataCount / numOfRows) + 1;

    }

    private URI setRequestUrl(Integer numOfRows, Integer requestPage) {
        return URI.create(baseUrl + "&serviceKey=" + serviceKey + "&numOfRows=" + numOfRows + "&pageNo=" + requestPage);
    }
}
