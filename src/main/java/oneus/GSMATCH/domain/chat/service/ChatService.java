package oneus.GSMATCH.domain.chat.service;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.chat.dto.response.*;
import oneus.GSMATCH.domain.chat.entity.ChatEntity;
import oneus.GSMATCH.domain.chat.entity.RoomEntity;
import oneus.GSMATCH.domain.chat.repository.ChatRepository;
import oneus.GSMATCH.domain.chat.repository.RoomRepository;
import oneus.GSMATCH.domain.request.entity.RequestEntity;
import oneus.GSMATCH.domain.request.repository.RequestRepository;
import oneus.GSMATCH.domain.user.entity.UserEntity;
import oneus.GSMATCH.domain.user.repository.UserRepository;
import oneus.GSMATCH.global.exception.CustomException;
import oneus.GSMATCH.global.exception.ErrorCode;
import oneus.GSMATCH.global.level.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final Point point;

    public RoomCreateResponse createRoom(Long toUserId, Long requestId) {
        RequestEntity request = requestRepository.findById(requestId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REQUEST));

        if (request.getRecipientsId().stream()
                .noneMatch(recipient -> recipient.equals(toUserId))) {
            throw new CustomException(ErrorCode.NOT_FOUND_REQUEST);
        }

        UserEntity fromUser = request.getAuthor();

        if (toUserId.equals(fromUser.getUsersId())) throw new CustomException(ErrorCode.DUPLICATED_USERNAME);

        UserEntity toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_MATCH_INFORMATION));

        if (roomRepository.existsByRequestAndToUser(request, toUser)) throw new CustomException(ErrorCode.DUPLICATED_CHAT);

        RoomEntity room = roomRepository.save(RoomEntity.createRoom(request, toUser, fromUser));

        point.requestPoint(toUserId, 10);

        return new RoomCreateResponse(room.getId());
    }

    public List<RoomResponse> roomListFind(UserEntity user) {
        return roomRepository.findByFromUserOrToUser(user, user).stream()
                .map(room ->
                        RoomResponse.builder()
                                .id(room.getId())
                                .roomName(room.getRequest().getTitle())
                                .partner(setPartner(room, user))
                                .build())
                .toList();
    }

    public RoomResponse roomFind(UserEntity user, Long roomId) {
        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REQUEST));

        if (!Objects.equals(room.getFromUser().getUsersId(), user.getUsersId()) &&
                !Objects.equals(room.getToUser().getUsersId(), user.getUsersId()))
            throw new CustomException(ErrorCode.NOT_FOUND_REQUEST);

        return RoomResponse.builder()
                .id(room.getId())
                .roomName(room.getRequest().getTitle())
                .partner(setPartner(room, user))
                .build();
    }

    public List<ChatResponse> chatListFind(Long roomId, UserEntity user) {

        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CHAT));

        if (!Objects.equals(room.getToUser().getUsersId(), user.getUsersId()) &&
                !Objects.equals(room.getFromUser().getUsersId(), user.getUsersId()))
            throw new CustomException(ErrorCode.DONT_ACCESS_CHAT);

         List<ChatResponse> chats = chatRepository.findAllByRoomId(roomId).stream()
                .map(chat -> ChatResponse.builder()
                        .id(chat.getId())
                        .sender(Sender.builder()
                                .senderId(chat.getSender().getUsersId())
                                .senderName(chat.getSender().getName())
                                .build())
                        .message(chat.getMessage())
                        .sendDate(chat.getSendDate())
                        .build()).toList();

         return chats;
    }

    public ChatResponse createChat(Long roomId, UserEntity user, String message) {
        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CHAT));

        ChatEntity chat = chatRepository.save(ChatEntity.createChat(room, user, message));

        return ChatResponse.builder()
                .id(chat.getId())
                .sender(Sender.builder()
                        .senderId(chat.getSender().getUsersId())
                        .senderName(chat.getSender().getName())
                        .build())
                .message(chat.getMessage())
                .sendDate(chat.getSendDate())
                .build();
    }

    public void roomDelete(Long roomId, UserEntity user) {
        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CHAT));

        if (!Objects.equals(room.getToUser().getUsersId(), user.getUsersId()) &&
                !Objects.equals(room.getFromUser().getUsersId(), user.getUsersId()))
            throw new CustomException(ErrorCode.DONT_ACCESS_CHAT);

        roomRepository.delete(room);
    }

    private Partner setPartner(RoomEntity room, UserEntity user) {
        return room.getToUser().getUsersId().equals(user.getUsersId()) ? Partner.builder()
                .id(room.getFromUser().getUsersId())
                .name(room.getFromUser().getName())
                .grade(room.getFromUser().getGrade())
                .major(room.getFromUser().getMajor())
                .type(room.getFromUser().getType())
                .build()
                : Partner.builder()
                .id(room.getToUser().getUsersId())
                .name(room.getToUser().getName())
                .grade(room.getToUser().getGrade())
                .major(room.getToUser().getMajor())
                .type(room.getToUser().getType())
                .build();
    }
}
