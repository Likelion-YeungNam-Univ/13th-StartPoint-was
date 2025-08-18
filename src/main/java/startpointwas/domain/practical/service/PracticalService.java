package startpointwas.domain.practical.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import startpointwas.domain.general.dto.FootTrafficDto;
import startpointwas.domain.general.dto.SimpleAnlsResponse;
import startpointwas.domain.practical.DongCode;
import startpointwas.domain.practical.client.GeneralClient;
import startpointwas.domain.practical.dto.PracticalDongAnls;
import startpointwas.domain.practical.dto.PracticalDongAnlsSlim;
import startpointwas.domain.practical.mapper.PracticalMapper;
import startpointwas.domain.practical.repository.PracticalRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PracticalService {

    private final GeneralClient generalClient;
    private final PracticalRepository practicalRepository;

    private final String analyNoDefault = "1143243";
    private final String simpleLocPrefix = "경상북도+경산시";

    public List<PracticalDongAnlsSlim> fetchAllDongsSlimByUpjong(String upjongCd) {
        return Arrays.stream(DongCode.values())
                .parallel()
                .map(d -> {

                    String admiCd = d.getCode();
                    PracticalDongAnls full = practicalRepository.get(upjongCd, admiCd);
                    if (full == null || full.getFootTrafficDto() == null || full.getSimpleAnlsDto() == null) {
                        try { full = buildAndCacheOne(upjongCd, admiCd); } catch (Exception ignored) {}
                    }

                    return PracticalMapper.toSlim(full);
                })
                .toList();
    }

    public Map<String, Object> fetchAllDongsByUpjongAsMap(String upjongCd) {
        List<PracticalDongAnlsSlim> items = fetchAllDongsSlimByUpjong(upjongCd);
        return Map.of("upjongCd", upjongCd, "items", items);
    }

    public PracticalDongAnls buildAndCacheOne(String upjongCd, String admiCd) {
        String dongName = DongCode.getDongName(admiCd);
        String simpleLoc = simpleLocPrefix + "+" + dongName;

        FootTrafficDto foot = generalClient.getFootTraffic(analyNoDefault, admiCd, upjongCd);
        SimpleAnlsResponse simple = generalClient.getSimpleAnls(admiCd, upjongCd, simpleLoc);

        PracticalDongAnls built = PracticalDongAnls.builder()
                .admiCd(admiCd)
                .upjongCd(upjongCd)
                .footTrafficDto(foot)
                .simpleAnlsDto(simple)
                .build();

        practicalRepository.put(upjongCd, admiCd, built);
        return built;
    }

    public void ensureValidAdmiCd(String admiCd) {
        if (!DongCode.exists(admiCd)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}