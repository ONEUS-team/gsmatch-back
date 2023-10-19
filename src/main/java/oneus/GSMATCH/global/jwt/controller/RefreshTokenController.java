package oneus.GSMATCH.global.jwt.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.global.jwt.service.RefreshTokenService;
import oneus.GSMATCH.global.security.UserDetailsImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @GetMapping("/refresh")
    public ResponseEntity<Void> refresh(HttpServletRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        refreshTokenService.refresh(request.getHeader("Refresh-Token"), userDetails.getUser().getUsersId());

        return null;
    }
}
