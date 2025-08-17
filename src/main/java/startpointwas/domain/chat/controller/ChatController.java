package startpointwas.domain.chat.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import startpointwas.domain.chat.dto.ChatRequest;
import startpointwas.domain.chat.exception.OpenAiApiException;
import startpointwas.domain.chat.service.ChatContextService;
import startpointwas.global.SessionConst;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final OpenAiChatModel openAiChatModel;
    private final ChatContextService contextSvc;
    private static final int MAX_PAIRS = 100;

    /**
     * 세션에서 userId 추출
     * 로그인 안 했으면 "NULLUSER" 리턴
     */
    private String resolveUserId(HttpSession session) {
        if (session == null) return "NULLUSER";
        Object uid = session.getAttribute(SessionConst.LOGIN_USER_UID);
        if (uid == null || uid.toString().isBlank()) {
            return "NULLUSER";
        }
        return uid.toString();
    }

    /**
     * 질문 요청 + 이전 대화 기록 포함 (Redis 기반)
     */
    @PostMapping("/ask")
    public Map<String, Object> askWithAllHistory(@RequestBody ChatRequest request,
                                                 HttpSession session) {
        Map<String, Object> res = new LinkedHashMap<>();
        try {
            String userId = resolveUserId(session); // ✅ 로그인 여부 확인

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

            String contextId = "ctx-" + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")) + "-" + userId;

            contextSvc.appendPair(userId, contextId, userMsg, answer);

            res.put("contextId", contextId);
            res.put("response", answer);
        } catch (Exception e) {
            res.clear();
            res.put("error", e.getMessage());
        }
        return res;
    }

    /**
     * 사용자 채팅 기록 전체 조회 (Redis 기반)
     */
    @GetMapping(path = "/ask", params = "userId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getUserChatHistory(
            @RequestParam(value = "userId", required = false) String userId,
            HttpSession session) {

        String effectiveUserId = (userId != null && !userId.isBlank())
                ? userId
                : resolveUserId(session);

        List<Map<String, String>> records = contextSvc.getAllChatsByUser(effectiveUserId);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("userId", effectiveUserId);
        result.put("count", records.size());
        result.put("history", records);

        return ResponseEntity.ok(result);
    }
}
