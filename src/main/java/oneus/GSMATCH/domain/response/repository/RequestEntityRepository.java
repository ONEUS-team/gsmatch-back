package oneus.GSMATCH.domain.response.repository;

import oneus.GSMATCH.domain.request.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import static oneus.GSMATCH.global.util.UserStateEnum.*;

import java.util.List;
import java.util.Optional;

public interface RequestEntityRepository extends JpaRepository<RequestEntity, Long> {
    List <RequestEntity> findByRecipientsIdContains(Long id);
}

