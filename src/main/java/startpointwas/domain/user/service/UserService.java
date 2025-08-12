package startpointwas.domain.user.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import startpointwas.domain.user.dto.SignUpReq;
import startpointwas.domain.user.dto.UserInfoDto;
import startpointwas.domain.user.entity.User;
import startpointwas.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /** 회원가입: 중복검사/암호화 없이 그대로 저장 */
    @Transactional
    public void signUp(SignUpReq dto) {

        User user = dto.toEntity(dto.getPassword());
        userRepository.save(user);
    }

    /** 유저 상세 조회 */
    @Transactional(readOnly = true)
    public UserInfoDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. id=" + id));
        return UserInfoDto.fromEntity(user);
    }

    /** 유저 삭제 */
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. id=" + id));
        userRepository.delete(user);
    }

    /** 로그인: 단순 문자열 비교 (암호화/보안 제거) */
    @Transactional(readOnly = true)
    public User login(String userId, String rawPassword) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("아이디 또는 비밀번호가 올바르지 않습니다."));
        if (!rawPassword.equals(user.getPassword())) {
            throw new RuntimeException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        return user;
    }

    /** 로그아웃: 세션 무효화 + JSESSIONID 만료 */
    public void logout(HttpSession session, HttpServletResponse response) {
        if (session != null) session.invalidate();

        ResponseCookie cookie = ResponseCookie.from("JSESSIONID", "")
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    /** id로 정보 조회 */
    @Transactional(readOnly = true)
    public UserInfoDto getUserInfo(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. id=" + id));
        return UserInfoDto.fromEntity(user);
    }
}
