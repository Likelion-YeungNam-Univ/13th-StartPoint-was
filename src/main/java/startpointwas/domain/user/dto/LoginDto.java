package startpointwas.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginDto {
    @Schema(description = "사용자 아이디", example = "likelion")
    @Pattern(regexp="^[a-zA-Z0-9_]{4,20}$", message="아이디는 4~20자 영문/숫자/밑줄만 허용")
    @NotBlank(message = "아이디는 필수 입력입니다.")
    private String userId;

    @Schema(description = "비밀번호", example = "abcd1234")
    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    private String password;
}
