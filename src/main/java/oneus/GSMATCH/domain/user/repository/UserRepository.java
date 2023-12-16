package oneus.GSMATCH.domain.user.repository;


import oneus.GSMATCH.domain.user.entity.UserEntity;
import static oneus.GSMATCH.global.util.UserStateEnum.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByName(String name);
    boolean existsByName(String name);
    boolean existsByEmail(String email);

    List<UserEntity> findByGradeInAndTypeAndUsersIdNot(List<Grade> grade, Type type, Long userId);

    List<UserEntity> findByGradeInAndTypeAndGenderInAndUsersIdNot(List<Grade> grade, Type type, List<Gender> gender, Long userId);

    List<UserEntity> findByGradeInAndMajorInAndUsersIdNot(List<Grade> grade, List<Major> majors, Long userId);

}