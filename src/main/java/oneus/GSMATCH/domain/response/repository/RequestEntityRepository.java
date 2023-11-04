package oneus.GSMATCH.domain.response.repository;

import oneus.GSMATCH.domain.request.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import static oneus.GSMATCH.global.util.UserStateEnum.*;

import java.util.List;
import java.util.Optional;

public interface RequestEntityRepository extends JpaRepository<RequestEntity, Long> {
    List<RequestEntity> findByRequestGradeInAndRequestGenderIn(List<Grade> requestGrade, List<Gender> requestGender);
    List<RequestEntity> findByRequestMajorInAndRequestGradeInAndRequestGenderIn(List<Major> requestMajor, List<Grade> requestGrade, List<Gender> requestGender);

    Optional<RequestEntity> findById(Long requestId);

    List<RequestEntity> findByRequestGradeInAndRequestGenderInAndRequestType(List<Grade> userGrade, List<Gender> userGender, RequestType requestType);

    List<RequestEntity> findByRequestMajorInAndRequestGradeInAndRequestGenderInAndRequestType(List<Major> userMajor, List<Grade> userGrade, List<Gender> userGender, RequestType requestType);
}

