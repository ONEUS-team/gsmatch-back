package oneus.GSMATCH.domain.user.service;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.user.dto.request.LoginRequest;
import oneus.GSMATCH.domain.user.dto.request.SignOutRequest;
import oneus.GSMATCH.domain.user.dto.request.SignupRequest;
import oneus.GSMATCH.domain.user.dto.response.RequestsResponse;
import oneus.GSMATCH.domain.user.dto.response.UserInfoResponse;
import oneus.GSMATCH.domain.user.entity.UserEntity;
import oneus.GSMATCH.domain.user.repository.UserRepository;
import oneus.GSMATCH.global.exception.CustomException;
import static oneus.GSMATCH.global.exception.ErrorCode.*;

import oneus.GSMATCH.global.util.UserRoleEnum;
import static oneus.GSMATCH.global.util.UserStateEnum.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.stream.Collectors;

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
        String email = signupRequestDto.getEmail();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        String emailIdRegex = "gsm.hs.kr";

        // 회원 중복 확인
        boolean isUserNameDuplicated = userRepository.existsByName(username);
        boolean isUserEmailDuplicated = userRepository.existsByEmail(email);
        if (isUserNameDuplicated || isUserEmailDuplicated) {
            throw new CustomException(DUPLICATED_USERNAME);
        }

        int index = email.indexOf("@");
        String emailDomain = email.substring(index + 1);
        if (!emailDomain.equals(emailIdRegex))
            throw new CustomException(NOT_GSM_EMAIL);

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
                .level(1) // default 레벨 1
                .point(0) // default 포인트 0
                .requestList(Collections.emptyList())
                .build();

        userRepository.save(user);
    }

    // 로그인
    public UserEntity login(LoginRequest loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        UserEntity user = userRepository.findByName(username).orElseThrow(
                () -> new CustomException(NOT_MATCH_INFORMATION)
        );

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(NOT_MATCH_INFORMATION);
        }

        return user;
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

    // 유저 정보 반환
    @Transactional
    public UserInfoResponse findUserInfo(UserEntity user) {
        UserEntity toDtoUser = userRepository.findById(user.getUsersId()).orElseThrow(() -> new CustomException(NOT_MATCH_INFORMATION));

        return UserInfoResponse.builder()
                .id(toDtoUser.getUsersId())
                .username(toDtoUser.getName())
                .gender(toDtoUser.getGender())
                .grade(toDtoUser.getGrade())
                .level(toDtoUser.getLevel())
                .point(toDtoUser.getPoint())
                .major(toDtoUser.getMajor())
                .type(toDtoUser.getType())
                .requestList(toDtoUser.getRequestList().stream()
                        .map(request -> {
                            String image = null;
                            if (request.getRequestImagesList() != null && !request.getRequestImagesList().isEmpty()) {
                                image = "/images/" + request.getRequestImagesList().get(0).getImageName();
                            }
                            return RequestsResponse.builder()
                                    .requestId(request.getRequestId())
                                    .title(request.getTitle())
                                    .content(request.getContent())
                                    .requestType(request.getRequestType())
                                    .authorName(request.getAuthor().getName())
                                    .image(image)
                                    .build();
                        }).collect(Collectors.toList()))
                .build();

    }

    // 유저 타입 수정
    @Transactional
    public void modifyUserType(Type type, UserEntity user) {
        UserEntity userEntity = userRepository.findById(user.getUsersId())
                .orElseThrow(() -> new CustomException(NOT_MATCH_INFORMATION));
        userEntity.modifyType(type);

        userRepository.save(userEntity);
    }
}
