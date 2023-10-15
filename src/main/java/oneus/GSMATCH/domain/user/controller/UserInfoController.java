package oneus.GSMATCH.domain.user.controller;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.user.dto.response.UserInfoResponse;
import oneus.GSMATCH.domain.user.service.UserService;
import oneus.GSMATCH.global.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserInfoController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserInfoResponse> findUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userService.findUserInfo(userDetails.getUser()));
    }
}
