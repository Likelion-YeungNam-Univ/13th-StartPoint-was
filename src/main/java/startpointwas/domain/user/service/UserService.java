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

    @Transactional
    public User signUp(SignUpReq dto) {
        User user = dto.toEntity(dto.getPassword());
        return userRepository.save(user);
    }
    
    @Transactional(readOnly = true)
    public UserInfoDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(RuntimeException::new);
        return UserInfoDto.fromEntity(user);
    }

}