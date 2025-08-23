package startpointwas.domain.practical.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import startpointwas.domain.practical.service.PracticalService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/practical")
@RequiredArgsConstructor
public class PracticalController {

    private final ChatClient chat;
    private final PracticalService practicalService;
    private final ObjectMapper om = new ObjectMapper();

    @GetMapping()
    public Map<String, Object> Practical(
            @RequestParam String admiCd,
            @RequestParam String startupUpjong
    ) {
        if (admiCd == null || admiCd.isBlank() ||
                startupUpjong == null || startupUpjong.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST
            );
        }
        practicalService.ensureValidAdmiCd(admiCd);

        Map<String, Object> practicalDongAnls = practicalService.fetchAllDongsByUpjongAsMap(startupUpjong);

        String slimJson;
        try {
            slimJson = om.writeValueAsString(practicalDongAnls);
        } catch (Exception e) {
            slimJson = "{\"error\":\"failed_to_serialize_slim\"}";
        }

        String system = """
        You are an expert AI analyst for Gyeongsan City.
        
        TOOLS:
        - fetch_practical_slim_by_dongs(upjongCd: string, admiCds: string[]) -> PracticalDongAnlsSlim[]
        
        RULES:
        - Use ONLY the JSON context first. Call the tool ONLY if the target or comparison districts are missing.
        Terminology: "admiCd"=administrative district code, "upjongCd"=business category code.
        """;

        String user = """
        TASKS:
        1) Feasibility score for upjongCd="%s" at admiCd="%s":
           - Compare RELATIVELY across all districts in the JSON context (and any tool results if called).
           - Return a SINGLE INTEGER 0..10.
        
        2) Benchmark Top-3 for the same upjongCd across Gyeongsan City:
           - Prioritize: dayAvg↑, saleAmt↑, saleCnt↑, prevYearRate↑, storeCntAdminNow↓
           - Tie-breaker: higher dayAvg first.
        
        You are an analyst. Use ONLY the provided JSON data.
        
        Output schema (must be a single valid JSON object; no extra text):
        {
          "feasibilityScore": number,
          "top3": [
            { "admiCd": string, "admiCd": string, "admiCd": string }
          ]
        }
        """.formatted(slimJson, startupUpjong, admiCd);

        OpenAiChatOptions opts = OpenAiChatOptions.builder()
                .responseFormat(ResponseFormat.builder()
                        .type(ResponseFormat.Type.JSON_OBJECT)
                        .build())
                .build();

        String content = chat.prompt()
                .options(opts)
                .advisors(new SimpleLoggerAdvisor())
                .system(system)
                .user(user)
                .call()
                .content();

        try {
            JsonNode node = om.readTree(content);
            int score = clamp0to10(node.get("feasibilityScore").asInt());
            List<String> top3 = om.convertValue( node.get("top3"), List.class);
            return Map.of("feasibilityScore", score, "top3", top3);
        } catch (Exception e) {
            return Map.of("feasibilityScore", 0, "top3", List.of());
        }
    }

    private int clamp0to10(Integer raw) {
        if (raw < 0) return 0;
        if (raw > 10) return 10;
        return raw;
    }
}