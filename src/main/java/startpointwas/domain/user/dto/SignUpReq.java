package startpointwas.domain.user.dto;

import startpointwas.domain.user.entity.User;
import startpointwas.domain.user.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "회원가입 요청 API")
public class SignUpReq {

    private String name;

    private String userId;

    private String password;

    private LocalDate birth;

    private String email;

    private Role role;

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
