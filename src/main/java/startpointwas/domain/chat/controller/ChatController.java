package startpointwas.domain.chat.controller;


import startpointwas.domain.chat.dto.ChatRequest;
import startpointwas.domain.chat.entity.ChatMessagePairEntity;
import startpointwas.domain.chat.exception.OpenAiApiException;
import startpointwas.domain.chat.service.ChatContextService;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class ChatController {

    private final OpenAiChatModel openAiChatModel;
    private final ChatContextService contextSvc;
    private static final int MAX_PAIRS = 100;

    public ChatController(OpenAiChatModel model, ChatContextService svc) {
        this.openAiChatModel = model;
        this.contextSvc = svc;
    }

    @PostMapping("/ask")
    public Map<String, Object> askWithAllHistory(@RequestBody ChatRequest request) {
        Map<String, Object> res = new LinkedHashMap<>();
        try {
            String userId = "jh";
            String userMsg = Optional.ofNullable(request.getMessage()).orElse("");
            if (userMsg.isBlank()) return Map.of("error", "EMPTY_MESSAGE");

            List<Message> msgs = contextSvc.buildMessagesForUser(userId);
            if (msgs.size() > MAX_PAIRS * 2) {
                msgs = msgs.subList(msgs.size() - MAX_PAIRS * 2, msgs.size());
            }

            msgs.add(new UserMessage(userMsg));

            String answer;
            try {
                answer = openAiChatModel.call(new Prompt(msgs))
                        .getResults().get(0).getOutput().getText();
            } catch (Exception e) {
                throw new OpenAiApiException("OpenAI 응답 처리 중 오류가 발생했습니다.", e);
            }

            String contextId = "ctx-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")) + "-" + userId;
            contextSvc.appendPair(userId, contextId, userMsg, answer);

            res.put("contextId", contextId);
            res.put("response", answer);
        } catch (Exception e) {
            res.clear();
            res.put("error", e.getMessage());
        }
        return res;
    }
    @GetMapping(path = "/ask", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getUserChatHistory(
            @RequestParam("userId") String userId) {

        List<ChatMessagePairEntity> records = contextSvc.getAllChatsByUser(userId);

        List<Map<String, Object>> history = new ArrayList<>();
        for (ChatMessagePairEntity p : records) {
            history.add(Map.of(
                    "question", p.getQuestion(),
                    "answer", p.getAnswer()
            ));
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("userId", userId);
        result.put("count", records.size());
        result.put("history", history);

        return ResponseEntity.ok(result);
    }

}