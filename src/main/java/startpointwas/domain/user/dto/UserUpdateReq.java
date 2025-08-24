package startpointwas.domain.user.dto;


import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import startpointwas.domain.user.entity.Role;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateReq {

    private String name;


    private LocalDate birth;


    private String email;

    private Role role;

    @Pattern(
            regexp = "^(010|011|016|017|018|019)-\\d{3,4}-\\d{4}$",
            message = "전화번호 형식이 올바르지 않습니다. 예: 010-1234-5678"
    )
    private String phone;

    private String password;
}