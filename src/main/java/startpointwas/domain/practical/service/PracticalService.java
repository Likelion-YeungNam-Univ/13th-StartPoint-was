package startpointwas.domain.practical.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import startpointwas.domain.general.dto.FootTrafficDto;
import startpointwas.domain.general.dto.SimpleAnlsResponse;
import startpointwas.domain.practical.DongCode;
import startpointwas.domain.practical.client.GeneralClient;
import startpointwas.domain.practical.dto.PracticalDongAnls;
import startpointwas.domain.practical.repository.PracticalRepository;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PracticalService {

    private final GeneralClient generalClient;
    private final PracticalRepository practicalRepository;

    private final String analyNoDefault = "1143243";
    private final String simpleLocPrefix = "경상북도+경산시";

    public List<PracticalDongAnls> fetchAllDongsByUpjong(String upjongCd) {
        return Arrays.stream(DongCode.values())
                .parallel()
                .map(d -> {

                    String admiCd = d.getCode();
                    String simpleLoc = simpleLocPrefix + "+" + d.getName();
                    FootTrafficDto foot = null;
                    SimpleAnlsResponse simple = null;

                    try {
                        foot = generalClient.getFootTraffic(analyNoDefault, admiCd, upjongCd);
                    } catch (Exception ignored) {}
                    try {
                        simple = generalClient.getSimpleAnls(admiCd, upjongCd, simpleLoc);
                    } catch (Exception ignored) {}

                    PracticalDongAnls built =  PracticalDongAnls.builder()
                            .admiCd(d.getCode())
                            .upjongCd(upjongCd)
                            .footTrafficDto(foot)
                            .simpleAnlsDto(simple)
                            .build();

                    try {
                        practicalRepository.put(upjongCd, admiCd, built);
                    } catch (Exception ignored) {}
                    
                    return built;
                })
                .toList();
    }

    public PracticalDongAnls buildAndCacheOne(String upjongCd, String admiCd) {
        String dongName = DongCode.getOrDefault(admiCd, admiCd);
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

}