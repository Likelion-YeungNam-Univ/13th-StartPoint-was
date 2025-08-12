package startpointwas.domain.user.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import startpointwas.domain.user.entity.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NameRoleRes {
    private String name;
    private String role;

    public static NameRoleRes from(User user) {
        return NameRoleRes.builder()
                .name(user.getName())
                .role(user.getRole() != null ? user.getRole().name() : null)
                .build();
    }
}
