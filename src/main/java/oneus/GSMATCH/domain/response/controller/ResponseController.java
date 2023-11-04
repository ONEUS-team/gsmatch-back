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
    public List<ResponseInfo> getRequest(@AuthenticationPrincipal UserDetailsImpl userDetails, RequestType requestType) {
        UserEntity user = userDetails.getUser();

        return responseService.getMatchingRequests(user, requestType);
    }

    @GetMapping("/{responseId}")
    public InfoResponse infoRequest(@RequestParam Long requestId) {
        return responseService.infoRequest(requestId);
    }

    @PostMapping("/likes")
    public boolean likes(@RequestParam Long requestId, @RequestParam Long userId) {
        return responseService.toggleLike(requestId, userId);
    }

}
