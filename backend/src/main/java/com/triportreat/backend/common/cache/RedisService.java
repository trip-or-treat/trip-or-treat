package com.triportreat.backend.common.cache;

import com.triportreat.backend.place.entity.Place;
import com.triportreat.backend.place.error.handler.exception.PlaceNotFoundException;
import com.triportreat.backend.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private final PlaceRepository placeRepository;

    public void increasePlaceView(Long id, Long views) {
        try {
            redisTemplate.opsForValue().setIfAbsent(id.toString(), views.toString());
            redisTemplate.opsForValue().increment(id.toString());
        } catch (RedisConnectionFailureException e) {
            log.error("Redis 연결 실패 : {}", e.getMessage());
        }
    }

    public void updatePlaceViewCounts() {
        try {
            ScanOptions options = ScanOptions.scanOptions().match("*").count(10).build();
            Cursor<byte[]> keys = redisTemplate.getConnectionFactory().getConnection().scan(options);

            while (keys.hasNext()) {
                String key = new String(keys.next());
                Long id = Long.parseLong(key);
                Long views = Long.parseLong(redisTemplate.opsForValue().getAndDelete(key));
                Place place = placeRepository.findById(id).orElseThrow(() -> new PlaceNotFoundException(id));
                place.updateViews(views);
                placeRepository.save(place);
            }
        } catch (RedisConnectionFailureException e) {
            log.error("Redis 연결 실패 : {}", e.getMessage());
        } catch (PlaceNotFoundException e) {
            log.error("Place를 찾을 수 없음 : {}", e.getMessage());
        }
    }
}
