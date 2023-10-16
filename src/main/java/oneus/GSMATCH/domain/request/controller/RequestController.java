package oneus.GSMATCH.domain.request.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.request.dto.request.CreateRequest;
import oneus.GSMATCH.domain.request.dto.request.RangeRequest;
import oneus.GSMATCH.domain.request.dto.response.RangeResponse;
import oneus.GSMATCH.domain.request.dto.response.RequestsResponse;
import oneus.GSMATCH.domain.request.service.RequestService;
import oneus.GSMATCH.global.security.UserDetailsImpl;
import oneus.GSMATCH.global.util.MsgResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<MsgResponseDto> saveRequest(@RequestBody @Valid CreateRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        requestService.saveRequest(request, userDetails.getUser());
        return ResponseEntity.ok(new MsgResponseDto("요청 보내기 완료", HttpStatus.CREATED.value()));
    }

    @PostMapping("/range")
    public ResponseEntity<String> rangeRequest(@RequestBody @Valid RangeRequest request) {

        return ResponseEntity.ok("ok");
    }

    @GetMapping
    public ResponseEntity<List<RequestsResponse>> findRequests(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<RequestsResponse> requestsResponses = requestService.findRequests(userDetails.getUser());

        return ResponseEntity.ok(requestsResponses);
    }
}
