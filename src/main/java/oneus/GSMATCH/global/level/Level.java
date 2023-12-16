package oneus.GSMATCH.global.level;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.user.entity.UserEntity;
import oneus.GSMATCH.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Level {
    private final UserRepository userRepository;

    void levelUp(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow();

        int point = user.getPoint();

        if (point >= 160) {
            processLevelUp(user, 5, 0);
        } else if (point >= 80) {
            processLevelUp(user, 4, 80);
        } else if (point >= 50) {
            processLevelUp(user, 3, 50);
        } else if (point >= 20) {
            processLevelUp(user, 2, 20);
        }

        userRepository.save(user);
    }

    private void processLevelUp(UserEntity user, int newLevel, int pointReduction) {
        if (newLevel > user.getLevel()){
            user.setLevel(newLevel);
            user.setPoint(user.getPoint() - pointReduction);
        }
    }
}
