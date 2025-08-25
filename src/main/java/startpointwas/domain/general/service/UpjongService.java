package startpointwas.domain.general.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import startpointwas.domain.general.dto.UpjongDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class UpjongService {
    public static List<UpjongDto> fetchUpjongList() {
        RestClient restClient = RestClient.create();
        List<UpjongDto> list = restClient.get()
                .uri("https://bigdata.sbiz.or.kr/gis/api/getHierarchyTpbizCode")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<UpjongDto>>() {});

        if (list == null)
            return List.of();

        List<UpjongDto> result = new ArrayList<>();
        for (UpjongDto dto : list) {
            if (dto.getUpjong3cd() != null && dto.getTpbiznm() != null) {
                String[] parts = dto.getTpbiznm().split(" > ");
                dto.setLargeCategory(parts[0]);
                dto.setMediumCategory(parts[1]);
                dto.setSmallCategory(parts[2]);
            }
            result.add(dto);
        }
        return result;
    }
}