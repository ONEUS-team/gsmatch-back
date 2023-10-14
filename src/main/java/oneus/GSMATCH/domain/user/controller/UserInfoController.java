package oneus.GSMATCH.domain.user.controller;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.user.dto.response.UserInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserInfoController {
    @GetMapping
    public ResponseEntity<UserInfoResponse> findUserInfo() {
    }
}
