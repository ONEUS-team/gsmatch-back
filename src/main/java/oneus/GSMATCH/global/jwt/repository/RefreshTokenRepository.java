package oneus.GSMATCH.global.jwt.repository;

import oneus.GSMATCH.global.jwt.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
