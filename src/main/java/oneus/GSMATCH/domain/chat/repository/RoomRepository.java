package oneus.GSMATCH.domain.chat.repository;


import oneus.GSMATCH.domain.chat.entity.RoomEntity;
import oneus.GSMATCH.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    List<RoomEntity> findByFromUserOrToUser(UserEntity fromUser, UserEntity toUser);
}
