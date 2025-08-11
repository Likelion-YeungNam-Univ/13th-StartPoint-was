package com.example.yu13thstartpointer.domain.user.dto;

import java.time.LocalDate;

import com.example.yu13thstartpointer.domain.user.entity.User;
import com.example.yu13thstartpointer.domain.user.entity.Role;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserInfoDto {
    private Long id;
    private String name;
    private String userId;
    private LocalDate birth;
    private String email;
    private Role role;
    private String phone;

    @Builder
    public UserInfoDto(Long id, String name, String userId, LocalDate birth,
                       String email, Role role, String phone) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.birth = birth;
        this.email = email;
        this.role = role;
        this.phone = phone;
    }

    public static UserInfoDto fromEntity(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .name(user.getName())
                .userId(user.getUserId())
                .birth(user.getBirth())
                .email(user.getEmail())
                .role(user.getRole())
                .phone(user.getPhone())
                .build();
    }
}
