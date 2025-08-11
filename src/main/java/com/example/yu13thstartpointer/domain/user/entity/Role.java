package com.example.yu13thstartpointer.domain.user.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    MENTOR("MENTOR"), MENTEE("MENTEE");

    private final String value;
}
