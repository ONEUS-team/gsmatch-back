package oneus.GSMATCH.domain.user.controller;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.user.dto.request.ModifyTypeRequest;
import oneus.GSMATCH.domain.user.dto.response.UserInfoResponse;
import oneus.GSMATCH.domain.user.service.UserService;
import oneus.GSMATCH.global.security.UserDetailsImpl;
import oneus.GSMATCH.global.util.MsgResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserInfoController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserInfoResponse> findUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userService.findUserInfo(userDetails.getUser()));
    }

    @PutMapping("/type")
    public ResponseEntity<MsgResponseDto> modifyUserType(@RequestBody ModifyTypeRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.modifyUserType(request.getType(), userDetails.getUser());
        return ResponseEntity.ok(new MsgResponseDto("유형변경 완료.", HttpStatus.OK.value()));
    }
}
