//package com.example.yu13thstartpointer.domain.user.dto;
//
//import jakarta.validation.constraints.*;
//import java.time.LocalDate;
//
//public record MyPageUpdateReq(
//        @NotBlank String name,
//        @NotBlank String userId,  // 닉네임/로그인ID
//        @NotBlank @Size(min = 8, message = "비밀번호는 8자 이상") String password,
//        @Past(message = "생년월일은 과거여야 합니다") LocalDate birth,
//        @Email @NotBlank String email,
//        @NotBlank String role,   // "USER" / "ADMIN"
//        @NotBlank String phone
//) {}