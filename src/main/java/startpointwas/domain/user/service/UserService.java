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

    @Transactional(readOnly = true)
    public User login(String userId, String rawPassword) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(RuntimeException::new);
        if (!rawPassword.equals(user.getPassword())) {
            throw new RuntimeException();
        }
        return user;
    }
    @Transactional
    public void updateUserPartial(Long userId, UserInfoDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);

        if (dto.getUserId() != null && !dto.getUserId().isBlank()) {
            user.setUserId(dto.getUserId());
        }
        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getBirth() != null) user.setBirth(dto.getBirth());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getPhone() != null) user.setPhone(dto.getPhone());
        if (dto.getRole() != null) user.setRole(dto.getRole());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(dto.getPassword());
        }

        userRepository.save(user);
    }


}