package startpointwas.domain.user.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import startpointwas.domain.user.entity.User;
import startpointwas.domain.user.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "회원가입 요청 API")
public class SignUpReq {

    private String name;

    private String userId;

    private String password;

    private LocalDate birth;

    private String email;

    private Role role;
    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    @Pattern(
            regexp = "^(010|011|016|017|018|019)-\\d{3,4}-\\d{4}$",
            message = "전화번호 형식이 올바르지 않습니다. 예: 010-1234-5678"
    )
    private String phone;

    public User toEntity(String encodedPassword) {
        return User.builder()
                .name(this.name)
                .userId(this.userId)
                .password(encodedPassword)
                .birth(this.birth)
                .email(this.email)
                .role(this.role)
                .phone(this.phone)
                .build();
    }
}
