package startpointwas.domain.practical.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.*;
import startpointwas.domain.practical.service.PracticalService;


import java.util.Map;

@RestController
@RequestMapping("/api/ai/practical")
@RequiredArgsConstructor
public class FeasibilityController {

    private final ChatClient chat;
    private final PracticalService practicalService;
    private final ObjectMapper om = new ObjectMapper();

    @GetMapping("/feasibility")
    public Map<String, Object> feasibility(
            @RequestParam String admiCd,
            @RequestParam(name = "startupUpjong") String upjongCd
    ) {
        Map<String, Object> slim = practicalService.fetchAllDongsByUpjongAsMap(upjongCd);

        String slimJson;
        try {
            slimJson = om.writeValueAsString(slim);
        } catch (Exception e) {
            slimJson = "{\"error\":\"failed_to_serialize_slim\"}";
        }

        String system = """
            You are a professional startup feasibility evaluator 
            specializing in Gyeongsan City, Gyeongsangbuk-do, South Korea.
            Use tools only when needed (minimal data).
            Provide only the feasibility score from 0 to 10.
            """;

        String user = """
            Evaluate startup feasibility for:
            - upjongCd: "%s"
            - dong(admiCd): "%s"
            
            Use the provided JSON context below. No external tool calls.
            Output only one integer (0-10).
            """.formatted(upjongCd, admiCd);

        String context = """
            [DATA CONTEXT - JSON]
            %s
            """.formatted(slimJson);

        String content = chat.prompt()
                .advisors(new SimpleLoggerAdvisor())
                .system(system)
                .user(user)
                .user(context)
                .call()
                .content();

        Integer score = clamp0to10(extractScore(content));


        return Map.of("feasibilityScore", score);
    }

    private Integer extractScore(String content) {
        if (content == null) return 0;

        try {
            JsonNode n = om.readTree(content);
            if (n.has("feasibilityScore"))
                return n.get("feasibilityScore").asInt();
        } catch (Exception ignore) {}

        try {
            String digits = content.replaceAll("[^0-9]", "").trim();
            if (!digits.isEmpty())
                return Integer.parseInt(digits);
        } catch (Exception ignore) {}

        return 0;
    }


    private int clamp0to10(Integer raw) {
        int v = (raw == null) ? 0 : raw;
        if (v < 0) return 0;
        if (v > 10) return 10;
        return v;
    }
}