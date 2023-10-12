package oneus.GSMATCH.domain.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.user.dto.request.LoginRequest;
import oneus.GSMATCH.domain.user.dto.request.SignOutRequest;
import oneus.GSMATCH.domain.user.dto.request.SignupRequest;
import oneus.GSMATCH.domain.user.entity.UserEntity;
import oneus.GSMATCH.domain.user.repository.UserRepository;
import oneus.GSMATCH.domain.user.service.UserService;
import oneus.GSMATCH.global.jwt.JwtUtil;
import oneus.GSMATCH.global.security.UserDetailsImpl;
import oneus.GSMATCH.global.util.MsgResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<MsgResponseDto> signup(@RequestBody @Valid SignupRequest request) {
        userService.signup(request);
        return ResponseEntity.ok(new MsgResponseDto("회원가입 완료", HttpStatus.CREATED.value()));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<MsgResponseDto> login(@RequestBody LoginRequest loginRequestDto, HttpServletResponse response) {
        UserEntity user = userService.login(loginRequestDto);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, JwtUtil.createToken(user.getName(), user.getRole()));
        return ResponseEntity.ok(new MsgResponseDto("로그인 완료", HttpStatus.OK.value()));
    }

    // 회원 탈퇴
    @DeleteMapping
    private ResponseEntity<MsgResponseDto> signOut(@RequestBody SignOutRequest signOutRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.signOut(signOutRequestDto, userDetails.getUser());
        return ResponseEntity.ok(new MsgResponseDto("회원탈퇴 완료", HttpStatus.RESET_CONTENT.value()));
    }
}