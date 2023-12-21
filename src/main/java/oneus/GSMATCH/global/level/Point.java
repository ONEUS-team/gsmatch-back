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

        if (point >= 110) {
            LevelUp(userEntity, 5);
        } else if (point >= 80) {
            LevelUp(userEntity, 4);
        } else if (point >= 50) {
            LevelUp(userEntity, 3);
        } else if (point >= 20) {
            LevelUp(userEntity, 2);
        }
    }

    private void LevelUp(UserEntity user, int newLevel) {
        if (newLevel > user.getLevel()){
            user.setLevel(newLevel);
        }
    }
}
