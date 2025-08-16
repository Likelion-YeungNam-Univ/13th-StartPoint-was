package startpointwas.domain.practical.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai/practical")
@RequiredArgsConstructor
public class FeasibilityController {

    private final ChatClient chat;
    private final ObjectMapper om = new ObjectMapper();

    @GetMapping("/feasibility")
    public Map<String, Object> feasibility(
            @RequestParam String admiCd,
            @RequestParam(name = "startupUpjong") String upjongCd
    ) {
        String system = """
        You are a professional startup feasibility evaluator 
        specializing in Gyeongsan City, Gyeongsangbuk-do, South Korea.
        Use tools only when needed (minimal data).
        Provide only the feasibility score from 0 to 10.
        """;

        String user = """
        Evaluate startup feasibility for upjongCd="%s" in dong(admiCd)="%s".
        If needed, call the tool: fetch_practical_slim_by_dongs(upjongCd="%s", admiCds=["%s"]).
    
        Output:
        - A single integer from 0 (very poor) to 10 (excellent).
        - No explanation, no extra text.
        """
                .formatted(upjongCd, admiCd, upjongCd, admiCd);

        String content = chat.prompt()
                .system(system)
                .user(user)
                .call()
                .content();

        Integer score = extractScore(content);

        return Map.of("feasibilityScore", score);
    }

    private Integer extractScore(String content) {
        try {
            JsonNode n = om.readTree(content);
            if (n.has("feasibilityScore"))
                return n.get("feasibilityScore").asInt();
        } catch (Exception ignore) {}

        try {
            String digits = content.replaceAll("[^0-9]", "").trim();
            if (!digits.isEmpty()) return Integer.parseInt(digits);
        } catch (Exception ignore) {}

        return 0;

    }
}