package startpointwas.domain.practical.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import startpointwas.domain.practical.dto.PracticalDongAnls;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class PracticalRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String format = "practical:anls:%s:%s";

    private String key(String upjongCd, String admiCd) {
        return String.format(format, upjongCd, admiCd);
    }

    public void put(String upjongCd, String admiCd, PracticalDongAnls value) {
        redisTemplate.opsForValue().set(key(upjongCd, admiCd), value, Duration.ofMinutes(5));
    }

    public PracticalDongAnls get(String upjongCd, String admiCd) {
        return (PracticalDongAnls) redisTemplate.opsForValue()
                .get(key(upjongCd, admiCd));
    }

}