package startpointwas.domain.practical.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import startpointwas.domain.practical.dto.PracticalDongAnls;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class PracticalRepository {

    private final RedisTemplate<String, PracticalDongAnls> practicalRedisTemplate;

    private static final String format = "practical:anls:%s:%s";

    private String key(String upjongCd, String admiCd) {
        return String.format(format, upjongCd, admiCd);
    }

    public void put(String upjongCd, String admiCd, PracticalDongAnls value) {
        practicalRedisTemplate.opsForValue().set(key(upjongCd, admiCd), value, Duration.ofMinutes(5));
    }

    public PracticalDongAnls get(String upjongCd, String admiCd) {
        return practicalRedisTemplate.opsForValue()
                .get(key(upjongCd, admiCd));
    }

}