// src/main/java/startpointwas/domain/upjong/service/UpjongService.java
package startpointwas.domain.general.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import startpointwas.domain.general.dto.UpjongDto;

import java.util.List;

@Service
public class UpjongService {
    public List<UpjongDto> fetchUpjongList() {
        RestClient restClient = RestClient.create();
        return restClient.get()
                .uri("https://bigdata.sbiz.or.kr/gis/api/getHierarchyTpbizCode")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<UpjongDto>>() {});
    }
}