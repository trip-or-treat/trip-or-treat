package com.triportreat.backend.place.repository.impl;

import com.triportreat.backend.place.domain.TourApiPlaceResponseDto.Item;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PlaceRepositoryJdbcImpl {

    private final JdbcTemplate jdbcTemplate;

    public List<Item> filteringValidPlaces(List<Item> places) {
        List<Long> validRegionIds = jdbcTemplate.queryForList("SELECT id FROM REGION", Long.class);
        List<Long> validContentTypeIds = jdbcTemplate.queryForList("SELECT id FROM CONTENT_TYPE", Long.class);
        List<String> validSubCategoryIds = jdbcTemplate.queryForList("SELECT id FROM SUB_CATEGORY", String.class);
        return places.stream()
                .filter(item -> validRegionIds.contains(item.getRegionId()))
                .filter(item -> validContentTypeIds.contains(item.getContentTypeId()))
                .filter(item -> validSubCategoryIds.contains(item.getSubCategoryId()))
                .toList();
    }

    public void batchUpsertPlace(List<Item> places) {
        String sql = "INSERT INTO PLACE (id, region_id, content_type_id, sub_category_id, " +
                "address, address_detail, name, main_category_id, mid_category_id, " +
                "image_origin, image_thumbnail, latitude, longitude) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "region_id = VALUES(region_id), " +
                "content_type_id = VALUES(content_type_id), " +
                "sub_category_id = VALUES(sub_category_id), " +
                "address = VALUES(address), " +
                "address_detail = VALUES(address_detail), " +
                "name = VALUES(name), " +
                "main_category_id = VALUES(main_category_id), " +
                "mid_category_id = VALUES(mid_category_id), " +
                "image_origin = VALUES(image_origin), " +
                "image_thumbnail = VALUES(image_thumbnail), " +
                "latitude = VALUES(latitude), " +
                "longitude = VALUES(longitude)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Item item = places.get(i);
                ps.setLong(1, item.getId());
                ps.setLong(2, item.getRegionId());
                ps.setLong(3, item.getContentTypeId());
                ps.setString(4, item.getSubCategoryId());
                ps.setString(5, item.getAddress());
                ps.setString(6, item.getAddressDetail());
                ps.setString(7, item.getName());
                ps.setString(8, item.getMainCategoryId());
                ps.setString(9, item.getMidCategoryId());
                ps.setString(10, item.getImageOrigin());
                ps.setString(11, item.getImageThumbnail());
                ps.setDouble(12, item.getLatitude());
                ps.setDouble(13, item.getLongitude());
            }

            @Override
            public int getBatchSize() {
                return places.size();
            }
        });
    }
}
