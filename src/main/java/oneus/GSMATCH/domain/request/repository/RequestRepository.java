package oneus.GSMATCH.domain.request.repository;

import oneus.GSMATCH.domain.request.entity.RequestEntity;
import oneus.GSMATCH.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
    boolean existsByRequestId(Long requestId);
    void deleteByRequestId(Long requestId);
    Integer countByAuthor(UserEntity user);
}
