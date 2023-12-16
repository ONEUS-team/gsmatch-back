package oneus.GSMATCH.global.level;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.user.entity.UserEntity;
import oneus.GSMATCH.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Point {
    private final UserRepository userRepository;
    private final Level level;

    public void requestPoint(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow();

        userEntity.setPoint(userEntity.getPoint() + 6);

        userRepository.save(userEntity);
        level.levelUp(id);
    }
}
