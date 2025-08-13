package startpointwas.domain.user.dto;

import lombok.Builder;
import lombok.Data;
import startpointwas.domain.user.entity.Role;
import startpointwas.domain.user.entity.User;

import java.time.LocalDate;

@Data
public class UserInfoDto {
    private String name;
    private String userId;
    private LocalDate birth;
    private String email;
    private Role role;
    private String phone;
    private String password;

    @Builder
    public UserInfoDto(String name, String userId, LocalDate birth,
                       String email, Role role, String phone, String password) {
        this.name = name;
        this.userId = userId;
        this.password = password;
        this.birth = birth;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public static UserInfoDto fromEntity(User user) {
        return UserInfoDto.builder()
                .name(user.getName())
                .userId(user.getUserId())
                .password(user.getPassword())
                .birth(user.getBirth())
                .phone(user.getPhone())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
