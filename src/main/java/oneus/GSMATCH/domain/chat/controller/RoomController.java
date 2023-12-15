package oneus.GSMATCH.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.chat.dto.request.RoomCreateRequest;
import oneus.GSMATCH.domain.chat.dto.response.ChatResponse;
import oneus.GSMATCH.domain.chat.dto.response.RoomCreateResponse;
import oneus.GSMATCH.domain.chat.dto.response.RoomResponse;
import oneus.GSMATCH.domain.chat.service.ChatService;
import oneus.GSMATCH.global.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final ChatService chatService;

    @PostMapping("/room")
    public ResponseEntity<RoomCreateResponse> createRoom(@RequestBody RoomCreateRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(chatService.createRoom(userDetails.getUser().getUsersId(), request.getRequestId()));
    }

    @GetMapping("/room")
    public ResponseEntity<List<RoomResponse>> findRoomList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(chatService.roomListFind(userDetails.getUser()));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<RoomResponse> findRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long roomId) {
        return ResponseEntity.ok(chatService.roomFind(userDetails.getUser(), roomId));
    }

    @GetMapping("/chat/{roomId}")
    public ResponseEntity<List<ChatResponse>> findChatList(@PathVariable Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(chatService.chatListFind(roomId, userDetails.getUser()));
    }
}
