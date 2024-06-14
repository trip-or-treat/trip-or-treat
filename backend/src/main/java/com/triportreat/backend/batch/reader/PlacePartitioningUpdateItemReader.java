package com.triportreat.backend.batch.reader;


import com.triportreat.backend.place.domain.TourApiPlaceResponseDto.Item;
import com.triportreat.backend.place.service.ExternalApiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@StepScope
public class PlacePartitioningUpdateItemReader implements ItemReader<List<Item>> {

    private final ExternalApiService externalApiService;

    @Value("#{stepExecutionContext[startPage]}")
    private int startPage;
    @Value("#{stepExecutionContext[endPage]}")
    private int endPage;
    @Value("#{stepExecutionContext[numOfRows]}")
    private int numOfRows;

    @Override
    public List<Item> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (startPage <= endPage) {
            List<Item> response = externalApiService.getPlaces(numOfRows, startPage);
            startPage++;
            return response;
        } else {
            return null;
        }

    }
}
