package oneus.GSMATCH.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.chat.dto.request.RoomCreateRequest;
import oneus.GSMATCH.domain.chat.dto.response.RoomCreateResponse;
import oneus.GSMATCH.domain.chat.dto.response.RoomResponse;
import oneus.GSMATCH.domain.chat.service.ChatService;
import oneus.GSMATCH.global.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final ChatService chatService;

    @PostMapping("/room")
    public ResponseEntity<RoomCreateResponse> createRoom(@RequestBody RoomCreateRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(chatService.createRoom(userDetails.getUser().getUsersId(), request.getRequestId()));
    }
}
