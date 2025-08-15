package startpointwas.domain.practical.tool;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import startpointwas.domain.practical.dto.PracticalDongAnls;
import startpointwas.domain.practical.dto.PracticalDongAnlsSlim;
import startpointwas.domain.practical.mapper.PracticalMapper;
import startpointwas.domain.practical.repository.PracticalRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PracticalTools {

    private final PracticalRepository repo;

    @Tool(
            name = "fetch_practical_slim_by_dongs",
            description = "업종코드와 동코드 목록으로 각 동의 PracticalDongAnlsSlim을 반환한다."
    )
    public List<PracticalDongAnlsSlim> fetchSlims(
            @ToolParam(description = "업종 코드") String upjongCd,
            @ToolParam(description = "행정동 코드 목록") List<String> admiCds
    ) {
        List<PracticalDongAnlsSlim> result = new ArrayList<>();
        for (String admiCd : admiCds) {
            PracticalDongAnls full = repo.get(upjongCd, admiCd);
            result.add(PracticalMapper.toSlim(full));
        }
        return result;
    }
}
