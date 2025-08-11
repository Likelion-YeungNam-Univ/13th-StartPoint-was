package com.example.yu13thstartpointer.domain.user.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record MyPagePatchReq(
        @Nullable String name,
        @Nullable String userId,
        @Size(min = 8, message = "비밀번호는 8자 이상") @Nullable String password,
        @Nullable LocalDate birth,
        @Email @Nullable String email,
        @Nullable String role,
        @Nullable String phone
) {}
