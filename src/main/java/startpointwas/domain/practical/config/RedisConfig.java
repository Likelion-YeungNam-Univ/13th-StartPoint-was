package startpointwas.domain.practical.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import startpointwas.domain.practical.dto.PracticalDongAnls;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    private final RedisProperties redisProperties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(
                new RedisStandaloneConfiguration(
                        redisProperties.getHost(),
                        redisProperties.getPort()
                )
        );
    }

    @Bean
    public RedisTemplate<String, PracticalDongAnls> practicalRedisTemplate(
            RedisConnectionFactory redisConnectionFactory, ObjectMapper objectMapper) {

        RedisTemplate<String, PracticalDongAnls> t = new RedisTemplate<>();
        t.setConnectionFactory(redisConnectionFactory);
        t.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer<PracticalDongAnls> vs =
                new Jackson2JsonRedisSerializer<>(PracticalDongAnls.class);
        vs.setObjectMapper(objectMapper);

        t.setValueSerializer(vs);
        t.afterPropertiesSet();
        return t;
    }

}