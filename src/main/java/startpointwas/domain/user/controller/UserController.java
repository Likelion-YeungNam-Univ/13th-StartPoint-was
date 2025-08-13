package startpointwas.domain.user.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import startpointwas.domain.user.dto.LoginDto;
import startpointwas.domain.user.dto.NameRoleRes;
import startpointwas.domain.user.dto.SignUpReq;
import startpointwas.domain.user.dto.UserInfoDto;
import startpointwas.domain.user.entity.User;
import startpointwas.domain.user.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private static final String SESSION_KEY = "LOGIN_USER_ID";
    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<NameRoleRes> signUp(@Valid @RequestBody SignUpReq req) {
        User saved = userService.signUp(req);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(NameRoleRes.from(saved));
    }

    @PostMapping("/login")
    public ResponseEntity<NameRoleRes> login(@Valid @RequestBody LoginDto req,
                                             HttpServletRequest request) {
        User user = userService.login(req.getUserId(), req.getPassword());

        HttpSession session = request.getSession(true);
        session.setAttribute(SESSION_KEY, user.getId());
        session.setMaxInactiveInterval(10 * 60 * 60);

        return ResponseEntity.ok(NameRoleRes.from(user));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session, HttpServletResponse response) {
        userService.logout(session, response);
        return ResponseEntity.ok("로그아웃에 성공하셨습니다.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInfoDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session) {
        Long userId = (Long) session.getAttribute(SESSION_KEY);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }

    @PatchMapping("/me")
    public ResponseEntity<Void> updateMyInfoPartial(HttpSession session,
                                                    @RequestBody UserInfoDto dto) {
        Long userId = (Long) session.getAttribute(SESSION_KEY);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        userService.updateUserPartial(userId, dto);
        return ResponseEntity.noContent().build();
    }

}