package com.example.yu13thstartpointer.domain.user.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private Role role;
    private String phone;

    // 관계 매핑 예시
//    @OneToMany(mappedBy = "user")
//    private List<Post> posts = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Comment> comments;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<BoardLike> boardLikes;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<CommentLike> commentLikes;

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
    public void update(String name, String userId, String password,
                       LocalDate birth, String email, Role role, String phone) {
        this.name = name;
        this.userId = userId;
        this.password = password;
        this.birth = birth;
        this.email = email;
        this.role = role;
        this.phone = phone;
    }
}