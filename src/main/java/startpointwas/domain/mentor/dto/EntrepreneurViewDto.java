package startpointwas.domain.mentor.dto;

import java.util.List;

import java.time.LocalDate;
import java.time.LocalTime;

public record EntrepreneurViewDto(
        Long id,
        String name,
        String storeName,
        String category,
        String area,
        String bio,
        int likeCount,
        String headline,
        String intro,
        LocalDate registeredDate,
        LocalTime registeredTime,
        List<String> keywords,
        List<String> topics
) {}