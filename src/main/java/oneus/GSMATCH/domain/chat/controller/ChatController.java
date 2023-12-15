package oneus.GSMATCH.domain.chat.controller;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.chat.dto.request.ChatMessage;
import oneus.GSMATCH.domain.chat.dto.response.ChatResponse;
import oneus.GSMATCH.domain.chat.service.ChatService;
import oneus.GSMATCH.domain.user.entity.UserEntity;
import oneus.GSMATCH.domain.user.repository.UserRepository;
import oneus.GSMATCH.global.exception.CustomException;
import oneus.GSMATCH.global.jwt.JwtUtil;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import static oneus.GSMATCH.global.exception.ErrorCode.INVALID_TOKEN;


@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @MessageMapping("/{roomId}")
    @SendTo("/room/{roomId}")
    public ChatResponse test(@DestinationVariable Long roomId, ChatMessage message) {
        if (!jwtUtil.validateToken(message.getToken().substring(7))) {
            throw new CustomException(INVALID_TOKEN);
        }

        Claims userinfo = jwtUtil.getUserInfoFromToken(message.getToken().substring(7));
        String username = userinfo.getSubject();
        UserEntity user =  userRepository.findByName(username)
                .orElseThrow(() -> new CustomException(INVALID_TOKEN));

        return chatService.createChat(roomId, user, message.getMessage());
    }
}
