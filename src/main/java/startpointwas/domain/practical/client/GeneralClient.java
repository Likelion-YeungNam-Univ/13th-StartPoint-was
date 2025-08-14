package startpointwas.domain.practical.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import startpointwas.domain.general.dto.FootTrafficDto;
import startpointwas.domain.general.dto.SimpleAnlsResponse;

@Component
public class GeneralClient {
    private final RestClient rest = RestClient.create();

    public FootTrafficDto getFootTraffic(String analyNo, String admiCd, String upjongCd) {
        return rest.get()
                .uri("http://localhost:8080/api/analysis/general/foot-traffic?analyNo={analyNo}&admiCd={admiCd}&upjongCd={upjongCd}",
                        analyNo, admiCd, upjongCd)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(FootTrafficDto.class);
    }

    public SimpleAnlsResponse getSimpleAnls(String admiCd, String upjongCd, String simpleLoc) {
        return rest.get()
                .uri("http://localhost:8080/api/analysis/general/simple-anls?admiCd={admiCd}&upjongCd={upjongCd}&simpleLoc={simpleLoc}",
                        admiCd, upjongCd, simpleLoc)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(SimpleAnlsResponse.class);
    }
}
