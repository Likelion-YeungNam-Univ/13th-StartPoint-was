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



}