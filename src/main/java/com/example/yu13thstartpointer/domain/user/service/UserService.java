package com.example.yu13thstartpointer.domain.user.service;

// === DTO ===
import com.example.yu13thstartpointer.domain.user.dto.LoginDto;        // 기존: CoCoNut_was.domains.user.reqdto.LoginUserDto
import com.example.yu13thstartpointer.domain.user.dto.SignUpReq;       // 기존: CoCoNut_was.domains.user.reqdto.CreateUserDto
import com.example.yu13thstartpointer.domain.user.dto.UserInfoDto;     // 기존: CoCoNut_was.domains.user.resdto.UserInfoDto
// TokenResDto 위치가 사진 패키지에 없으므로 아래 둘 중 하나를 선택해서 사용


// === ENTITY / REPOSITORY ===
import com.example.yu13thstartpointer.domain.user.entity.Role;
import com.example.yu13thstartpointer.domain.user.entity.User;         // 기존: CoCoNut_was.domains.user.entity.User
import com.example.yu13thstartpointer.domain.user.repository.UserRepository;

// === 예외 (프로젝트에 맞게 선택) ===
// 아래는 제가 제안했던 위치(global.exception). 실제 패키지명에 맞춰 하나만 사용하세요.
import com.example.yu13thstartpointer.exception.CustomException;
import com.example.yu13thstartpointer.exception.ErrorCode;
import com.example.yu13thstartpointer.domain.user.dto.MyPagePatchReq;
// === 기타 의존 ===
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // BCrypt 등 Bean 등록 필요

    public void signUp(SignUpReq dto) {
        if(existsByEmail(dto.getEmail()))
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXIST);
        if(existsByUserId(dto.getUserId()))
            throw new CustomException(ErrorCode.NICKNAME_ALREADY_EXIST);

        User user = dto.toEntity(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
    }
//이메일 중복 체크
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // 닉네임 중복확인
    public boolean existsByUserId(String userId){
        return userRepository.existsByUserId(userId);
    }

    // 유저 상세조회
    public UserInfoDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                //() -> new IllegalArgumentException("DB id : " + id + " 를 가진 유저가 존재하지 않습니다.")
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        return UserInfoDto.fromEntity(user);
    }

    @Transactional
    public UserInfoDto patchMyInfo(Long userId, MyPagePatchReq req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. id=" + userId));

        if (req.name() != null) user.setName(req.name());

        if (req.userId() != null && !req.userId().equals(user.getUserId())) {
            if (userRepository.existsByUserId(req.userId())) throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
            user.setUserId(req.userId());
        }

        if (req.password() != null && !req.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(req.password()));
        }

        if (req.birth() != null) user.setBirth(req.birth());

        if (req.email() != null && !req.email().equals(user.getEmail())) {
            if (userRepository.existsByEmail(req.email())) throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
            user.setEmail(req.email());
        }

        if (req.role() != null) user.setRole(Role.valueOf(req.role().toUpperCase()));

        if (req.phone() != null) user.setPhone(req.phone());

        return UserInfoDto.fromEntity(user);
    }


    //유저 삭제
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                //() -> new IllegalArgumentException("DB id : " + id + " 를 가진 유저가 존재하지 않습니다.")
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        userRepository.delete(user);
    }

    /* =========================
       로그인 (비밀번호 검증)
       - 세션을 쓸 경우 컨트롤러에서 반환된 User의 id를 세션에 저장하면 됩니다.
     ========================= */
    @Transactional(readOnly = true)
    public User login(String userId, String rawPassword) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        return user;
    }


    /** 로그아웃(세션 무효화 + JSESSIONID 쿠키 만료) */
    public void logout(HttpSession session, HttpServletResponse response) {
        if (session != null) {
            session.invalidate();
        }

        // 브라우저 측 JSESSIONID 제거(즉시 만료)
        ResponseCookie cookie = ResponseCookie.from("JSESSIONID", "")
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // Spring Security 사용 중이면 SecurityContext도 정리
        SecurityContextHolder.clearContext();
    }

    /** 내 정보 조회(세션에 저장된 id 활용 시) */
    public UserInfoDto getUserInfo(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return UserInfoDto.fromEntity(user);
    }
}


