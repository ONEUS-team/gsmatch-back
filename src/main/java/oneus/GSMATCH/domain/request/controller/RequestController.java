package oneus.GSMATCH.domain.request.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.request.dto.request.ModifyRequest;
import oneus.GSMATCH.domain.request.dto.request.RequestRequest;
import oneus.GSMATCH.domain.request.dto.response.InfoResponse;
import oneus.GSMATCH.domain.request.dto.response.RangeResponse;
import oneus.GSMATCH.domain.request.service.RequestService;
import oneus.GSMATCH.global.security.UserDetailsImpl;
import oneus.GSMATCH.global.util.MsgResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<MsgResponseDto> saveRequest(@RequestBody @Valid RequestRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        requestService.saveRequest(request, userDetails.getUser());
        return ResponseEntity.ok(new MsgResponseDto("요청 보내기 완료.", HttpStatus.CREATED.value()));
    }

    @PostMapping("/range")
    public ResponseEntity<RangeResponse> rangeRequest(@RequestBody @Valid RequestRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(requestService.rangeRequest(request, userDetails.getUser()));
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<InfoResponse> infoRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(requestService.infoRequest(requestId));
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<MsgResponseDto> modifyRequest(@PathVariable Long requestId,
                                                        @RequestBody ModifyRequest request,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        requestService.modifyRequest(requestId, request, userDetails.getUser());
        return ResponseEntity.ok(new MsgResponseDto("요청 수정 완료.", HttpStatus.OK.value()));
    }

    @DeleteMapping("/{requestid}")
    public ResponseEntity<MsgResponseDto> deleteRequest(@PathVariable Long requestid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        requestService.deleteRequest(requestid, userDetails.getUser());
        return ResponseEntity.ok(new MsgResponseDto("요청 삭제 완료.", HttpStatus.OK.value()));
    }
}