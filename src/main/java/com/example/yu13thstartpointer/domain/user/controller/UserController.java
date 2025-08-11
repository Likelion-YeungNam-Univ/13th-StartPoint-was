package com.example.yu13thstartpointer.domain.user.controller;

import com.example.yu13thstartpointer.domain.user.dto.LoginDto;
import com.example.yu13thstartpointer.domain.user.dto.MyPagePatchReq;
import com.example.yu13thstartpointer.domain.user.dto.SignUpReq;
import com.example.yu13thstartpointer.domain.user.dto.UserInfoDto;
import com.example.yu13thstartpointer.domain.user.entity.User;
import com.example.yu13thstartpointer.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.yu13thstartpointer.web.SessionConst;

import com.example.yu13thstartpointer.web.SessionConst;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController implements UserApi {
    private final UserService userService;

    // 1. 회원가입(회원추가)
    @Override
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpReq dto){
        userService.signUp(dto);
        return ResponseEntity.ok().build();
    }

    // 2. 회원조회
    @Override
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getUser(@PathVariable Long user_id){
        return ResponseEntity.ok(userService.getUser(user_id));
    }

    // 3. 회원탈퇴(삭제)
    @Override
    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long user_id){
        userService.deleteUser(user_id);
        return ResponseEntity.ok().build();
    }

    /** 로그인: 세션에 USER_ID 저장 */
    /** 로그인 (userId + password, 세션 저장) */
    @PostMapping("/login")
    public ResponseEntity<UserInfoDto> login(@Valid @RequestBody LoginDto dto,
                                             HttpSession session) {
        // UserService는 userId 기반으로 로그인 검증
        User user = userService.login(dto.getUserId(), dto.getPassword());

        // 세션에 사용자 식별자 저장
        session.setAttribute(SessionConst.USER_ID, user.getId());
        // 필요 시 세션 만료시간(초) 지정
        session.setMaxInactiveInterval(30 * 60);

        return ResponseEntity.ok(UserInfoDto.fromEntity(user));
    }
    /** 로그아웃: 세션 무효화 + 쿠키 만료 */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session, HttpServletResponse response) {
        userService.logout(session, response);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/me")
    public ResponseEntity<?> patchMe(@Valid @RequestBody MyPagePatchReq req, HttpSession session) {
        Object id = session.getAttribute(SessionConst.USER_ID);
        if (id == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        return ResponseEntity.ok(userService.patchMyInfo((Long) id, req));
    }
    /** 내 정보 조회(세션 필요) */
    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session) {
        Object id = session.getAttribute(SessionConst.USER_ID);
        if (id == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        return ResponseEntity.ok(userService.getUserInfo((Long) id));
    }


}
