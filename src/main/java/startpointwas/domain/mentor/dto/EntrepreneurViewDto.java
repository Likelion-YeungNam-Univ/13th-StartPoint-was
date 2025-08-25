package startpointwas.domain.mentor.dto;

import java.util.List;

import java.time.LocalDate;
import java.time.LocalTime;

public record EntrepreneurViewDto(
        Long id,
        String name,
        String storeName,
        String largeCategory,
        String area,
        String bio,
        int likeCount,
        String headline,
        String intro,
        LocalDate registeredDate,
        LocalTime registeredTime,
        String keywords,
        List<String> topics
) {}