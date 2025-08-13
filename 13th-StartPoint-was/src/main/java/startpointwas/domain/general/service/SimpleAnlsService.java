package startpointwas.domain.general.service;

import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import startpointwas.domain.general.dto.SimpleAnlsResponse;

import java.net.URI;

@Service
public class SimpleAnlsService {

    private final RestClient restClient;
    private final String baseUrl = "https://bigdata.sbiz.or.kr";

    public SimpleAnlsService() {
        var httpClient = HttpClients.custom().build();
        var factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .build();
    }

    public SimpleAnlsResponse fetchAvgAmtInfo(String admiCd, String upjongCd, String simpleLoc) {
        URI uri = UriComponentsBuilder
                .fromUriString(baseUrl + "/gis/simpleAnls/getAvgAmtInfo.json")
                .queryParam("admiCd", admiCd)
                .queryParam("upjongCd", upjongCd)
                .queryParam("simpleLoc", simpleLoc)
                .queryParam("bizonNumber", "")
                .queryParam("bizonName", "")
                .queryParam("bzznType", "1")
                .queryParam("xtLoginId", "")
                .build()
                .toUri();

        return restClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(SimpleAnlsResponse.class);
    }
}