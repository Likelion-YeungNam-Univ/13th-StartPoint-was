package startpointwas.domain.user.dto;


import startpointwas.domain.user.entity.User;
import startpointwas.domain.user.entity.Role;



import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "회원가입 요청 API")
public class SignUpReq {

    @Schema(description = "사용자 이름", example = "홍길동")
    @NotBlank(message = "이름은 필수 입력입니다.")
    private String name;

    @Schema(description = "로그인ID/유저ID", example = "lion13")
    @NotBlank(message = "아이디는 필수 입력입니다.")
    private String userId;

    @Schema(description = "비밀번호", example = "abcd1234")
    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    @Schema(description = "생년월일", example = "2000-01-13")
    @NotNull(message = "생년월일은 필수 입력입니다.")
    private LocalDate birth;

    @Schema(description = "이메일", example = "likelion13@gmail.com")
    @Email(message = "이메일 형식을 맞춰주세요.")
    @NotBlank(message = "이메일은 필수 입력입니다.")
    private String email;

    @Schema(description = "역할", example = "ROLE_USER")
    @NotBlank(message = "역할 지정은 필수 사항입니다.")
    private String role; // 문자열로 받아 Role.valueOf로 변환

    @Schema(description = "전화번호", example = "010-1234-5678")
    @NotBlank(message = "전화번호는 필수 입력입니다.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식은 010-xxxx-xxxx이어야 합니다.")
    private String phone;

    public User toEntity(String encodedPassword) {
        return User.builder()
                .name(this.name)
                .userId(this.userId)
                .password(encodedPassword)
                .birth(this.birth)
                .email(this.email)
                .role(Role.valueOf(this.role))
                .phone(this.phone)
                .build();
    }
}