package startpointwas.domain.user.dto;

import startpointwas.domain.user.entity.User;
import startpointwas.domain.user.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "회원가입 요청 API")
public class SignUpReq {

    @Schema(description = "사용자 이름", example = "홍길동")
    private String name;

    @Schema(description = "로그인ID/유저ID", example = "lion13")
    private String userId;

    @Schema(description = "비밀번호", example = "abcd1234")
    private String password;

    @Schema(description = "생년월일", example = "2000-01-13")
    private LocalDate birth;

    @Schema(description = "이메일", example = "likelion13@gmail.com")
    private String email;

    @Schema(description = "역할", example = "MENTOR/MENTEE")
    private String role;

    @Schema(description = "전화번호", example = "010-1234-5678")
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
