package startpointwas.domain.practical.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import startpointwas.domain.general.dto.FootTrafficDto;
import startpointwas.domain.general.dto.SimpleAnlsResponse;
import startpointwas.domain.practical.DongCode;
import startpointwas.domain.practical.client.GeneralClient;
import startpointwas.domain.practical.dto.PracticalDongAnls;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PracticalService {

    private final GeneralClient generalClient;

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

                    return PracticalDongAnls.builder()
                            .admiCd(d.getCode())
                            .upjongCd(upjongCd)
                            .footTrafficDto(foot)
                            .simpleAnlsDto(simple)
                            .build();
                })
                .toList();
    }
}