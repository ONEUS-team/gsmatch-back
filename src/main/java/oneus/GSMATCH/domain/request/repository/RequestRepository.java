package oneus.GSMATCH.domain.request.repository;

import oneus.GSMATCH.domain.request.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
}
