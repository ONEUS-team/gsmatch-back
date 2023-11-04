package oneus.GSMATCH.domain.response.controller;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.request.dto.response.InfoResponse;
import oneus.GSMATCH.domain.response.dto.ResponseInfo;
import oneus.GSMATCH.domain.response.service.ResponseService;
import oneus.GSMATCH.domain.user.entity.UserEntity;
import oneus.GSMATCH.global.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static oneus.GSMATCH.global.util.UserStateEnum.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/response")
public class ResponseController {
    private final ResponseService responseService;

    @GetMapping
    public List<ResponseInfo> getRequest(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return responseService.getRequest(userDetails);
    }

    @GetMapping("/{responseId}")
    public InfoResponse infoRequest(@PathVariable Long responseId) {
        return responseService.infoRequest(responseId);
    }

    @PostMapping("/{responseId}/likes")
    public void likes(@PathVariable Long responseId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        responseService.toggleLike(responseId, userDetails.getUser().getUsersId());
    }

}
