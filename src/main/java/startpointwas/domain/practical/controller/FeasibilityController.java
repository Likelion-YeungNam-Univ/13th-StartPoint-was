package startpointwas.domain.practical.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai/practical")
@RequiredArgsConstructor
public class FeasibilityController {

    private final ChatClient chat;

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

        var answer = chat.prompt()
                .system(system)
                .user(user)
                .call()
                .content();

        return Map.of(
                "admiCd", admiCd,
                "upjongCd", upjongCd,
                "answer", answer
        );
    }
}