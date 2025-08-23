package startpointwas.domain.user.dto;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import startpointwas.domain.user.entity.Role;
import startpointwas.domain.user.entity.User;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDto {
    private String name;
    private String userId;
    private LocalDate birth;
    private String email;
    private Role role;
    private String phone;
    private String password;

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
