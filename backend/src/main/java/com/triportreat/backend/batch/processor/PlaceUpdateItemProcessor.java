package com.triportreat.backend.batch.processor;


import com.triportreat.backend.place.domain.TourApiPlaceResponseDto.Item;
import com.triportreat.backend.place.repository.impl.PlaceRepositoryJdbcImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlaceUpdateItemProcessor implements ItemProcessor<List<Item>, List<Item>> {

    private final PlaceRepositoryJdbcImpl placeRepositoryJdbc;

    @Override
    public List<Item> process(List<Item> items) throws Exception {
        return placeRepositoryJdbc.filteringValidPlaces(items);
    }
}
