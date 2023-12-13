package oneus.GSMATCH.domain.chat.repository;

import oneus.GSMATCH.domain.chat.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    List<ChatEntity> findAllByRoomId(Long roomId);
}
