package com.triportreat.backend.batch.writer;


import com.triportreat.backend.place.domain.TourApiPlaceResponseDto.Item;
import com.triportreat.backend.place.repository.impl.PlaceRepositoryJdbcImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PlaceUpdateItemWriter implements ItemWriter<List<Item>> {

    private final PlaceRepositoryJdbcImpl placeRepositoryJdbc;

    @Override
    public void write(Chunk<? extends List<Item>> chunk) throws Exception {
        placeRepositoryJdbc.batchUpsertPlace(chunk.getItems().get(0));
    }
}
