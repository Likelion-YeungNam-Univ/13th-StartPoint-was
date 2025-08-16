package startpointwas.domain.chat.service;

import startpointwas.domain.chat.entity.ChatMessagePairEntity;
import startpointwas.domain.chat.repository.ChatMessagePairRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatContextService {

    private final ChatMessagePairRepository pairRepo;
    private final StringRedisTemplate redis;
    private final ObjectMapper om;

    private static String keyList(String ctx) {
        return "chat:ctx:" + ctx;
    }

    private static String keyUserCtx(String userId) {
        return "chat:user:" + userId + ":ctx";
    }

    @Transactional
    public void appendPair(String userId, String contextId, String q, String a) {
        ChatMessagePairEntity saved = pairRepo.save(new ChatMessagePairEntity(userId, contextId, q, a));
        try {
            var payload = Map.of(
                    "id", saved.getId(),
                    "userId", userId,
                    "contextId", contextId,
                    "question", nz(q),
                    "answer", nz(a),
                    "createdAt", saved.getCreatedAt().toString()
            );
            String json = om.writeValueAsString(payload);
            redis.opsForList().rightPush(keyList(contextId), json);
            redis.opsForSet().add(keyUserCtx(userId), contextId);
            redis.expire(keyList(contextId), Duration.ofDays(7));
        } catch (Exception ignored) {}
    }

    @Transactional(readOnly = true)
    public List<Message> buildMessagesForUser(String userId) {
        Set<String> contextIds = redis.opsForSet().members(keyUserCtx(userId));
        if (contextIds == null || contextIds.isEmpty()) {
            return fallbackToMySQLByUser(userId);
        }

        List<Map<String, String>> allMessages = new ArrayList<>();
        for (String contextId : contextIds) {
            List<String> jsonList = redis.opsForList().range(keyList(contextId), 0, -1);
            for (String json : jsonList) {
                try {
                    Map<String, String> m = om.readValue(json, Map.class);
                    allMessages.add(m);
                } catch (Exception ignored) {}
            }
        }

        allMessages.sort(Comparator.comparing(m -> m.get("createdAt")));

        List<Message> messages = new ArrayList<>();
        for (Map<String, String> m : allMessages) {
            messages.add(new UserMessage(nz(m.get("question"))));
            messages.add(new AssistantMessage(nz(m.get("answer"))));
        }
        return messages;
    }

    private List<Message> fallbackToMySQLByUser(String userId) {
        List<ChatMessagePairEntity> records = pairRepo.findByUserIdOrderByIdAsc(userId);
        List<Message> messages = new ArrayList<>();
        for (ChatMessagePairEntity p : records) {
            messages.add(new UserMessage(p.getQuestion()));
            messages.add(new AssistantMessage(p.getAnswer()));
        }
        return messages;
    }

    @Transactional(readOnly = true)
    public List<ChatMessagePairEntity> getAllChatsByUser(String userId) {
        return pairRepo.findByUserIdOrderByIdAsc(userId);
    }

    private static String nz(String s) {
        return s == null ? "" : s;
    }
}