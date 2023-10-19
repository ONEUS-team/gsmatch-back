package oneus.GSMATCH.global.jwt.service;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.global.exception.CustomException;
import oneus.GSMATCH.global.exception.ErrorCode;
import oneus.GSMATCH.global.jwt.JwtUtil;
import oneus.GSMATCH.global.jwt.entity.RefreshToken;
import oneus.GSMATCH.global.jwt.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void saveRefreshToken(String token, Long userId) {
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(token)
                .userId(userId)
                .build();

        refreshTokenRepository.save(refreshToken);

    }
}
