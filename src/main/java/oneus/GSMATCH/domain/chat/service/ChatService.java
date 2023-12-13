package oneus.GSMATCH.domain.chat.service;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.chat.dto.response.RoomCreateResponse;
import oneus.GSMATCH.domain.chat.entity.RoomEntity;
import oneus.GSMATCH.domain.chat.repository.ChatRepository;
import oneus.GSMATCH.domain.chat.repository.RoomRepository;
import oneus.GSMATCH.domain.request.entity.RequestEntity;
import oneus.GSMATCH.domain.request.repository.RequestRepository;
import oneus.GSMATCH.domain.user.entity.UserEntity;
import oneus.GSMATCH.domain.user.repository.UserRepository;
import oneus.GSMATCH.global.exception.CustomException;
import oneus.GSMATCH.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;

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

        RoomEntity room = roomRepository.save(RoomEntity.createRoom(request, toUser, fromUser));

        return new RoomCreateResponse(room.getId());
    }
}
