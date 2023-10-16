package oneus.GSMATCH.domain.user.repository;


import oneus.GSMATCH.domain.user.entity.UserEntity;
import static oneus.GSMATCH.global.util.UserStateEnum.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByName(String name);
    boolean existsByName(String name);


    List<UserEntity> findByTypeAndUsersIdNot(Type type, Long userId);


    List<UserEntity> findByMajorInAndUsersIdNot(List<Major> majors, Long userId);

    List<UserEntity> findByGradeAndTypeAndUsersIdNot(Grade grade, Type type, Long userId);
    List<UserEntity> findByGradeAndMajorInAndUsersIdNot(Grade grade, List<Major> majors, Long userId);
}