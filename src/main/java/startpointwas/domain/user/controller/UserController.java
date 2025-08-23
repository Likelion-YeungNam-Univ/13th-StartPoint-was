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
import startpointwas.global.SessionConst;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

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
        session.setAttribute(SessionConst.LOGIN_USER_UID, user.getUserId());
        session.setMaxInactiveInterval(10 * 60 * 60);
        return ResponseEntity.ok(NameRoleRes.from(user));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session, HttpServletResponse response) {
        userService.logout(session, response);
        return ResponseEntity.ok("로그아웃에 성공하셨습니다.");
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session) {
        String uid = (String) session.getAttribute(SessionConst.LOGIN_USER_UID);
        if (uid == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        return ResponseEntity.ok(userService.getUserInfoByUserId(uid));
    }

    @PatchMapping("/me")
    public ResponseEntity<Void> updateMyInfoPartial(HttpSession session,
                                                    @RequestBody UserInfoDto dto) {
        String uid = (String) session.getAttribute(SessionConst.LOGIN_USER_UID);
        if (uid == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var updated = userService.updateUserPartialByUserId(uid, dto);
        session.setAttribute(SessionConst.LOGIN_USER_UID, updated.getUserId());
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}