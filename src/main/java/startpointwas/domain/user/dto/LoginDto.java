package startpointwas.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {
    @NotBlank
    private String userId;
    @NotBlank
    private String password;
}

