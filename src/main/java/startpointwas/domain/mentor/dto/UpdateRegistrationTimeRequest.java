package startpointwas.domain.mentor.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record UpdateRegistrationTimeRequest(
        LocalDate registeredDate,
        LocalTime registeredTime
) {}