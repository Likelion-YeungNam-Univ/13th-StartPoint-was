package startpointwas.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import startpointwas.domain.user.dto.LoginDto;
import startpointwas.domain.user.dto.SignUpReq;
import startpointwas.domain.user.dto.UserInfoDto;

/**
 * 사용자 회원가입/로그인/조회/삭제 API 스펙.
 * 구현체(예: UserController)는 이 인터페이스를 implements 하세요.
 */
@Validated
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserApi {

    //세션 저장 키
    String SESSION_KEY = "LOGIN_USER_ID";


    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> signUp(@Valid @RequestBody SignUpReq req);


    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserInfoDto> login(@Valid @RequestBody LoginDto req,
                                      HttpServletRequest request);

    @PostMapping("/logout")
    ResponseEntity<Void> logout(HttpSession session, HttpServletResponse response);

    @GetMapping("/{id}")
    ResponseEntity<UserInfoDto> getUser(@PathVariable("id") Long id);

    @GetMapping("/me")
    ResponseEntity<?> me(HttpSession session);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id);
}
