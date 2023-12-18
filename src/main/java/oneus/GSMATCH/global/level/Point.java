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
    public void requestPoint(Long id, Integer point) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_MATCH_INFORMATION));

        userEntity.setPoint(userEntity.getPoint() + point);

        levelUp(userEntity);
        userRepository.save(userEntity);
    }

    private void levelUp(UserEntity userEntity) {
        int point = userEntity.getPoint();

        if (point >= 160) {
            processLevelUp(userEntity, 5, 0);
        } else if (point >= 80) {
            processLevelUp(userEntity, 4, 80);
        } else if (point >= 50) {
            processLevelUp(userEntity, 3, 50);
        } else if (point >= 20) {
            processLevelUp(userEntity, 2, 20);
        }
    }

    private void processLevelUp(UserEntity user, int newLevel, int pointReduction) {
        if (newLevel > user.getLevel()){
            user.setLevel(newLevel);
            user.setPoint(user.getPoint() - pointReduction);
        }
    }
}
