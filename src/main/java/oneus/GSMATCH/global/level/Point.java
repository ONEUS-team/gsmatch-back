package oneus.GSMATCH.global.level;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.user.entity.UserEntity;
import oneus.GSMATCH.domain.user.repository.UserRepository;
import oneus.GSMATCH.global.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static oneus.GSMATCH.global.exception.ErrorCode.NOT_MATCH_INFORMATION;

@Service
@RequiredArgsConstructor
public class Point {
    private final UserRepository userRepository;

    @Transactional
    public void requestPoint(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_MATCH_INFORMATION));

        userEntity.setPoint(userEntity.getPoint() + 6);

        levelUp(id);
        userRepository.save(userEntity);
    }

    private void levelUp(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_MATCH_INFORMATION));

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
    }

    private void processLevelUp(UserEntity user, int newLevel, int pointReduction) {
        if (newLevel > user.getLevel()){
            user.setLevel(newLevel);
            user.setPoint(user.getPoint() - pointReduction);
        }
    }
}
