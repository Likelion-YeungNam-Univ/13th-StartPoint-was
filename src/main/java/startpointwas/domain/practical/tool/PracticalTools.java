package startpointwas.domain.practical.tool;

import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import startpointwas.domain.practical.dto.PracticalDongAnls;
import startpointwas.domain.practical.repository.PracticalRepository;
import org.springframework.ai.tool.annotation.Tool;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PracticalTools {

    private final PracticalRepository practicalRepository;

    public PracticalTools(PracticalRepository practicalRepository) {
        this.practicalRepository = practicalRepository;
    }

    @Tool(
            name = "getDongAnalysis",
            description = """
        Fetch practical analysis for given upjongCd and admiCd list from Redis.
        Return only essential fields to keep tokens small.
        If an item is missing in Redis, skip it.
        """
    )
    public List<SlimDongAnls> getDongAnalysis(
            @ToolParam(description = "업종 코드, 예: G20405") String upjongCd,
            @ToolParam(description = "지역 코드, 예: 47290253") List<String> admiCds
    ) {
        return admiCds.stream()
                .distinct()
                .map(admi -> practicalRepository.get(upjongCd, admi))
                .map(SlimDongAnls::from)
                .collect(Collectors.toList());
    }

    public record SlimDongAnls(
            String admiCd,
            String upjongCd,
            String stdYm,
            Integer dayAvg,
            Integer saleAmt,
            Integer saleCnt
    ) {
        public static SlimDongAnls from(PracticalDongAnls src) {
            var pop = Optional.ofNullable(src.getFootTrafficDto())
                    .map(f -> f.getPopulation()).orElse(null);
            var simple = src.getSimpleAnlsDto();

            return new SlimDongAnls(
                    src.getAdmiCd(),
                    src.getUpjongCd(),
                    simple != null ? simple.getStdYm() : (pop != null ? pop.getStdYm() : null),
                    pop != null ? safeInt(pop.getDayAvg()) : null,
                    simple != null ? safeInt(simple.getSaleAmt()) : null,
                    simple != null ? safeInt(simple.getSaleCnt()) : null
            );
        }

        private static Integer safeInt(Object v) {
            if (v == null) return null;
            if (v instanceof Number n) return n.intValue();
            try { return Integer.parseInt(String.valueOf(v)); } catch (Exception e) { return null; }
        }
    }
}