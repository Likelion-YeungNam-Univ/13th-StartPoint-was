package startpointwas.domain.mentor.service;

import startpointwas.domain.general.dto.UpjongDto;
import startpointwas.domain.general.service.UpjongService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoryService {
    public Map<String, String> groupByLargeCategory() {
        List<UpjongDto> list = UpjongService.fetchUpjongList();
        return list.stream()
                .collect(Collectors.groupingBy(
                        UpjongDto::getLargeCategory,
                        Collectors.mapping(
                                UpjongDto::getMediumCategory,
                                Collectors.collectingAndThen(
                                        Collectors.toSet(),
                                        set -> String.join(",", set)
                                )
                        )
                ));
    }

    public String getMediumByLarge(String largeCategory) {
        return groupByLargeCategory()
                .getOrDefault(largeCategory, "");
    }
}
