package oneus.GSMATCH.domain.response.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.response.dto.response.InfoRequest;
import oneus.GSMATCH.domain.response.dto.response.ResponseId;
import oneus.GSMATCH.domain.response.dto.response.ResponseInfo;
import oneus.GSMATCH.domain.response.service.ResponseService;
import oneus.GSMATCH.global.security.UserDetailsImpl;
import oneus.GSMATCH.global.util.MsgResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/response")
public class ResponseController {
    private final ResponseService responseService;

    @GetMapping
    public ResponseEntity<List<ResponseInfo>> getRequest(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(responseService.getRequest(userDetails));
    }

    @GetMapping("/{responseId}")
    public ResponseEntity<InfoRequest> infoRequest(@PathVariable Long responseId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(responseService.infoRequest(responseId, userDetails));
    }

    @PostMapping("/likes")
<<<<<<< HEAD
    public ResponseEntity<MsgResponseDto> likes(@RequestBody @Valid ResponseId requestId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        responseService.toggleLike(requestId, userDetails.getUser().getUsersId());
        return ResponseEntity.ok(new MsgResponseDto("좋아요 누르기 성공.", HttpStatus.OK.value()));
=======
    public MsgResponseDto likes(@RequestBody Long responseId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        responseService.toggleLike(responseId, userDetails.getUser().getUsersId());
        return new MsgResponseDto("좋아요 누르기 성공.", HttpStatus.OK.value());
>>>>>>> 67ca039f8d3716e5f449cd387cd135cd4467d315
    }

}
