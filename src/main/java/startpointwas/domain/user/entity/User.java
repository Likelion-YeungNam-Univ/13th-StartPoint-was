package startpointwas.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String userId;
    private String password;
    private LocalDate birth;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String phone;
    @Builder
    public User(String name,String userId, String password, LocalDate birth, String email, Role role, String phone) {
        this.name = name;
        this.userId = userId;
        this.password = password;
        this.birth = birth;
        this.email = email;
        this.role = role;
        this.phone = phone;
    }
}