package oneus.GSMATCH.global.jwt.service;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.global.exception.CustomException;
import static oneus.GSMATCH.global.exception.ErrorCode.*;
import oneus.GSMATCH.global.jwt.JwtUtil;
import oneus.GSMATCH.global.jwt.entity.RefreshToken;
import oneus.GSMATCH.global.jwt.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void saveRefreshToken(String token, Long userId) {

        if (refreshTokenRepository.existsByUserId(userId))
                refreshTokenRepository.deleteByUserId(userId);

        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(token)
                .userId(userId)
                .build();

        refreshTokenRepository.save(refreshToken);
    }

    public void refresh(String refreshToken) {
        RefreshToken savedToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new CustomException(NOT_MATCH_INFORMATION));

        if (!jwtUtil.validateToken(savedToken.getRefreshToken()) || !savedToken.getRefreshToken().equals(refreshToken)) {
            refreshTokenRepository.deleteByRefreshToken(refreshToken);
            throw new CustomException(INVALID_TOKEN);
        }
    }
}
