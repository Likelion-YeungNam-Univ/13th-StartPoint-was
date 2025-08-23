package startpointwas.domain.chat.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Set;
import java.util.Comparator;

@Repository
@RequiredArgsConstructor
public class ChatRedisRepository {

    private final StringRedisTemplate redis;
    private final ObjectMapper om;

    private static String keyList(String ctx) {
        return "chat:ctx:" + ctx;
    }

    private static String keyUserCtx(String userId) {
        return "chat:user:" + userId + ":ctx";
    }

    public void save(String userId, String contextId, String question, String answer) {
        try {
            var payload = Map.of(
                    "id", UUID.randomUUID().toString(),
                    "userId", userId,
                    "contextId", contextId,
                    "question", question == null ? "" : question,
                    "answer", answer == null ? "" : answer,
                    "createdAt", new Date().toString()
            );

            String json = om.writeValueAsString(payload);
            redis.opsForList().rightPush(keyList(contextId), json);
            redis.opsForSet().add(keyUserCtx(userId), contextId);
            redis.expire(keyList(contextId), Duration.ofHours(3));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, String>> findAllByUserId(String userId) {
        Set<String> contextIds = redis.opsForSet().members(keyUserCtx(userId));
        if (contextIds == null || contextIds.isEmpty()) return List.of();

        List<Map<String, String>> all = new ArrayList<>();
        for (String contextId : contextIds) {
            List<String> jsonList = redis.opsForList().range(keyList(contextId), 0, -1);
            for (String json : jsonList) {
                try {
                    Map<String, String> m = om.readValue(json, Map.class);
                    all.add(m);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        all.sort(Comparator.comparing(m -> m.get("createdAt")));
        return all;
    }

}
