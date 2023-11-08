package oneus.GSMATCH.domain.response.service;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.request.dto.response.Author;
import oneus.GSMATCH.domain.request.entity.RequestEntity;
import oneus.GSMATCH.domain.response.dto.response.InfoRequest;
import oneus.GSMATCH.domain.response.dto.response.ResponseId;
import oneus.GSMATCH.domain.response.dto.response.ResponseInfo;
import oneus.GSMATCH.domain.response.repository.RequestEntityRepository;
import oneus.GSMATCH.global.exception.CustomException;
import oneus.GSMATCH.global.exception.ErrorCode;
import oneus.GSMATCH.global.security.UserDetailsImpl;
import oneus.GSMATCH.global.util.MsgResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final RequestEntityRepository repository;

    @Transactional
    public List<ResponseInfo> getRequest(UserDetailsImpl userDetails) {
        List<RequestEntity> userRequest = repository.findByRecipientsIdContains(userDetails.getUser().getUsersId());

        return userRequest.stream()
                .map(request -> mapToRequestInfo(request, userDetails.getUser().getUsersId()))
                .toList();
    }

    private ResponseInfo mapToRequestInfo(RequestEntity request, Long id) {
        return ResponseInfo.builder()
                .responseId(request.getRequestId())
                .title(request.getTitle())
                .content(request.getContent())
                .requestOnly(request.getRequestOnly())
                .authorName(request.getAuthor().getName())
                .requestType(request.getRequestType())
                .likes(request.getLikesId().contains(id))
                .build();
    }

    @Transactional
    public InfoRequest infoRequest(Long requestId, UserDetailsImpl userDetails) {
        RequestEntity request = repository.findById(requestId)
                .orElseThrow(() ->  new CustomException(ErrorCode.NOT_OK_REQUEST));

        if (request.getRecipientsId().contains(userDetails.getUser().getUsersId())) {
            return InfoRequest.builder()
                    .responseId(request.getRequestId())
                    .title(request.getTitle())
                    .content(request.getContent())
                    .requestOnly(request.getRequestOnly())
                    .author(Author.builder()
                            .name(request.getAuthor().getName())
                            .grade(request.getAuthor().getGrade())
                            .type(request.getAuthor().getType())
                            .level(request.getAuthor().getLevel())
                            .build())
                    .likes(request.getLikesId().contains(userDetails.getUser().getUsersId()))
                    .requestType(request.getRequestType()).build();
        }
        else return null;

    }

    @Transactional
    public void toggleLike(ResponseId requestId, Long userId) {
        RequestEntity request = repository.findById(requestId.getResponseId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_OK_REQUEST));

        assert request != null;

        List<Long> likesIds = request.getLikesId();
        boolean like = likesIds.contains(userId);
        if (like) {
            likesIds.remove(userId);
        } else {
            likesIds.add(userId);
        }
    }
}
