package oneus.GSMATCH.global.level;

import oneus.GSMATCH.domain.user.entity.UserEntity;
import oneus.GSMATCH.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Point {
    private final UserRepository userRepository;

    @Autowired
    public Point(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void requestPoint(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow();

        userEntity.setPoint(userEntity.getPoint() + 6);

        userRepository.save(userEntity);
    }
}
