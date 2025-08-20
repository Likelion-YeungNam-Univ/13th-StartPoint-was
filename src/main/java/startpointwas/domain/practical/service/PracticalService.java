package startpointwas.domain.practical.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import startpointwas.domain.general.dto.FootTrafficDto;
import startpointwas.domain.general.dto.SimpleAnlsResponse;
import startpointwas.domain.general.service.FootTrafficService;
import startpointwas.domain.general.service.SimpleAnlsService;
import startpointwas.domain.practical.DongCode;
import startpointwas.domain.practical.dto.PracticalDongAnls;
import startpointwas.domain.practical.dto.PracticalDongAnlsSlim;
import startpointwas.domain.practical.mapper.PracticalMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PracticalService {

    private final FootTrafficService footTrafficService;
    private final SimpleAnlsService simpleAnlsService;

    private final String analyNoDefault = "1143243";
    private final String simpleLocPrefix = "경상북도+경산시";

    public List<PracticalDongAnlsSlim> fetchAllDongsSlimByUpjong(String upjongCd) {
        return Arrays.stream(DongCode.values())
                .map(DongCode::getCode)
                .map(admiCd -> {
                    try {
                        PracticalDongAnls full = buildOne(upjongCd, admiCd);
                        return PracticalMapper.toSlim(full);
                    } catch (Exception ignored) { }
                    return null;
                })
                .filter(java.util.Objects::nonNull)
                .toList();
    }

    public Map<String, Object> fetchAllDongsByUpjongAsMap(String upjongCd) {
        List<PracticalDongAnlsSlim> items = fetchAllDongsSlimByUpjong(upjongCd);
        return Map.of("upjongCd", upjongCd, "items", items);
    }

    public PracticalDongAnls buildOne(String upjongCd, String admiCd) {
        String dongName = DongCode.getDongName(admiCd);
        String simpleLoc = simpleLocPrefix + "+" + dongName;

        FootTrafficDto foot = footTrafficService.fetchFootTrafficInfo(analyNoDefault, admiCd, upjongCd);
        SimpleAnlsResponse simple = simpleAnlsService.fetchAvgAmtInfo(admiCd, upjongCd, simpleLoc);

        PracticalDongAnls built = PracticalDongAnls.builder()
                .admiCd(admiCd)
                .upjongCd(upjongCd)
                .footTrafficDto(foot)
                .simpleAnlsDto(simple)
                .build();

        return built;
    }

    public void ensureValidAdmiCd(String admiCd) {
        if (!DongCode.exists(admiCd)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}