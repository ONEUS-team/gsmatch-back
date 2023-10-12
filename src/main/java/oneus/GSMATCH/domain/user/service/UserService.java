package oneus.GSMATCH.domain.user.service;

import ch.qos.logback.core.spi.ErrorCodes;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.user.dto.request.LoginRequest;
import oneus.GSMATCH.domain.user.dto.request.SignOutRequest;
import oneus.GSMATCH.domain.user.dto.request.SignupRequest;
import oneus.GSMATCH.domain.user.entity.UserEntity;
import oneus.GSMATCH.domain.user.repository.UserRepository;
import oneus.GSMATCH.global.exception.CustomException;
import static oneus.GSMATCH.global.exception.ErrorCode.*;

import oneus.GSMATCH.global.jwt.JwtUtil;
import oneus.GSMATCH.global.util.UserRoleEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.admin-token}")
    private String adminToken;

    public void signup(SignupRequest signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // 회원 중복 확인
        Optional<UserEntity> checkUsername = userRepository.findByName(username);
        if (checkUsername.isPresent()) {
            throw new CustomException(DUPLICATED_USERNAME);
        }

        // 사용자 ROLE 확인 (admin = true 일 경우 아래 코드 수행)
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!(adminToken.equals(signupRequestDto.getAdminToken()))) {
                throw new CustomException(NOT_MATCH_INFORMATION);
            }

            role = UserRoleEnum.ADMIN;
        }

        UserEntity user = UserEntity.builder()
                .name(username)
                .password(password)
                .role(role)
                .email(signupRequestDto.getEmail())
                .grade(signupRequestDto.getGrade())
                .gender(signupRequestDto.getGender())
                .major(signupRequestDto.getMajor())
                .type(signupRequestDto.getType())
                .level(1) // default 레벨 1
                .point(0) // default 포인트 0
                .build();

        userRepository.save(user);
    }

    // 로그인
    public void login(LoginRequest loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        Optional<UserEntity> user = userRepository.findByName(username);

        if (user.isEmpty()) {
            throw new CustomException(NOT_MATCH_INFORMATION);
        }

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new CustomException(NOT_MATCH_INFORMATION);
        }

        // Header 에 key 값과 Token 담기
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, JwtUtil.createToken(user.get().getName(), user.get().getRole()));
    }

    // 회원탈퇴
    public void signOut(SignOutRequest signOutRequestDto, UserEntity user) {

        // 사용자명 일치 여부 확인
        user = userRepository.findByName(signOutRequestDto.getUsername()).orElseThrow(
                () -> new CustomException(NOT_MATCH_INFORMATION)
        );

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(signOutRequestDto.getPassword(), user.getPassword())) {
            throw new CustomException(NOT_MATCH_INFORMATION);
        }

        userRepository.deleteById(user.getUsersId());
    }
}
