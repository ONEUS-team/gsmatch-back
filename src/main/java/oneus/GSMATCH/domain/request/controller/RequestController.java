package oneus.GSMATCH.domain.request.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.request.dto.request.CreateRequest;
import oneus.GSMATCH.domain.request.dto.request.RangeRequest;
import oneus.GSMATCH.domain.request.dto.response.RangeResponse;
import oneus.GSMATCH.global.util.MsgResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/request")
public class RequestController {

    @PostMapping
    public ResponseEntity<MsgResponseDto> createRequest(@RequestBody @Valid CreateRequest request) {

        return ResponseEntity.ok(new MsgResponseDto("요청 보내기 완료", HttpStatus.CREATED.value()));
    }

    @PostMapping("/range")
    public ResponseEntity<String> rangeRequest(@RequestBody @Valid RangeRequest request) {

        return ResponseEntity.ok("ok");
    }
}
