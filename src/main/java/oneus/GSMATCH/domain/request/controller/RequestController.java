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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<MsgResponseDto> saveRequest(
            @Valid @RequestPart RequestRequest request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        // 이미지 개수 검사
        if (images != null && !images.isEmpty() && images.size() > 3) {
            return ResponseEntity.badRequest().body(new MsgResponseDto("이미지는 최대 3장까지 업로드 가능합니다.", HttpStatus.BAD_REQUEST.value()));
        }

        // 이미지 포함 여부에 따라 다르게 처리
        if (images != null && !images.isEmpty()) {
            requestService.saveRequest(request, userDetails.getUser(), images);
        } else {
            requestService.saveRequestWithoutImages(request, userDetails.getUser());
        }

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
    public ResponseEntity<MsgResponseDto> modifyRequest(@PathVariable Long requestId, @RequestBody ModifyRequest request,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        requestService.modifyRequest(requestId, request, userDetails.getUser());
        return ResponseEntity.ok(new MsgResponseDto("요청 수정 완료.", HttpStatus.OK.value()));
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<MsgResponseDto> deleteRequest(@PathVariable Long requestId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        requestService.deleteRequest(requestId, userDetails.getUser());
        return ResponseEntity.ok(new MsgResponseDto("요청 삭제 완료.", HttpStatus.OK.value()));
    }
}
