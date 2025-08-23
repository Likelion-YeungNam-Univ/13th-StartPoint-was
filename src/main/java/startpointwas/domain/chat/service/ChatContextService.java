package startpointwas.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import startpointwas.domain.chat.repository.ChatRedisRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatContextService {

    private final ChatRedisRepository redisRepo;

    @Transactional
    public void appendPair(String userId, String contextId, String q, String a) {
        redisRepo.save(userId, contextId, q, a);
    }

    @Transactional(readOnly = true)
    public List<Message> buildMessagesForUser(String userId) {
        List<Map<String, String>> allMessages = redisRepo.findAllByUserId(userId);

        List<Message> messages = new ArrayList<>();
        for (Map<String, String> m : allMessages) {
            messages.add(new UserMessage(nz(m.get("question"))));
            messages.add(new AssistantMessage(nz(m.get("answer"))));
        }
        return messages;
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> getAllChatsByUser(String userId) {
        return redisRepo.findAllByUserId(userId);
    }

    private static String nz(String s) {
        return s == null ? "" : s;
    }
}
