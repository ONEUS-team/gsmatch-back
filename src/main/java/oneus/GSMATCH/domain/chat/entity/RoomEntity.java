package oneus.GSMATCH.domain.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import oneus.GSMATCH.domain.request.entity.RequestEntity;
import oneus.GSMATCH.domain.user.entity.UserEntity;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder
public class RoomEntity {
    @Id
    @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private RequestEntity request;

    @ManyToOne
    @JoinColumn(name = "to_user")
    private UserEntity toUser;

    @ManyToOne
    @JoinColumn(name = "from_user")
    private UserEntity fromUser;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatEntity> chatEntityList;

    public static RoomEntity createRoom(RequestEntity request, UserEntity toUser, UserEntity fromUser) {
        return RoomEntity.builder()
                .request(request)
                .toUser(toUser)
                .fromUser(fromUser)
                .build();
    }

}