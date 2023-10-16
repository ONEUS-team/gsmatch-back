package oneus.GSMATCH.image.repository;

import oneus.GSMATCH.image.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}
