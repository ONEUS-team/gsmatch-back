package oneus.GSMATCH.domain.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import oneus.GSMATCH.domain.user.entity.UserEntity;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ChatEntity {

    @Id @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    @ManyToOne
    private UserEntity sender;

    @Column(columnDefinition = "TEXT")
    private String message;

    @CreatedDate
    @Column(name = "send_date", updatable = false)
    private LocalDateTime sendDate;

    @Builder
    public ChatEntity(RoomEntity room, UserEntity sender, String message) {
        this.room = room;
        this.sender = sender;
        this.message = message;
        this.sendDate = LocalDateTime.now();
    }


    public static ChatEntity createChat(RoomEntity room, UserEntity sender, String message) {
        return ChatEntity.builder()
                .room(room)
                .sender(sender)
                .message(message)
                .build();
    }
}
