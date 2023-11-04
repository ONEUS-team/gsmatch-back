package oneus.GSMATCH.domain.response.service;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.request.dto.response.Author;
import oneus.GSMATCH.domain.request.dto.response.InfoResponse;
import oneus.GSMATCH.domain.request.entity.RequestEntity;
import oneus.GSMATCH.domain.response.dto.ResponseInfo;
import oneus.GSMATCH.domain.response.repository.RequestEntityRepository;
import oneus.GSMATCH.domain.user.entity.UserEntity;
import oneus.GSMATCH.global.util.UserStateEnum.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final RequestEntityRepository repository;

    @Transactional
    public List<ResponseInfo> getMatchingRequests(UserEntity user, RequestType requestType) {
        List<RequestEntity> allRequests = repository.findAll();

        List<ResponseInfo> info = new ArrayList<>();
        if (requestType == RequestType.TYPE) {
            info = allRequests.stream()
                    .filter(request -> request.getAuthor().getType().equals(user.getType()))
                    .filter(request -> request.getRequestGender().contains(user.getGender()))
                    .filter(request -> request.getRequestGrade().contains(user.getGrade()))
                    .map(this :: mapToRequestInfo)
                    .collect(Collectors.toList());
        } else if (requestType == RequestType.STUDY) {
            info = allRequests.stream().filter(request -> request.getRequestType() == requestType)
                    .filter(request -> request.getRequestGender().contains(user.getGender()))
                    .filter(request -> request.getRequestGrade().contains(user.getGrade()))
                    .filter(request -> request.getRequestMajor().contains(user.getMajor()))
                    .map(this::mapToRequestInfo)
                    .collect(Collectors.toList());
        }

        return info;
    }

    private ResponseInfo mapToRequestInfo (RequestEntity request) {
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setTitle(request.getTitle());
        responseInfo.setId(request.getRequestId());
        responseInfo.setAuthorName(request.getAuthor().getName());
        responseInfo.setRequestOnly(request.getRequestOnly());

        return responseInfo;
    }

    @Transactional
    public InfoResponse infoRequest(Long requestId) {
        RequestEntity request = repository.findById(requestId)
                .orElseThrow();

        return InfoResponse.builder()
                .id(request.getRequestId())
                .title(request.getTitle())
                .content(request.getContent())
                .author(Author.builder()
                        .name(request.getAuthor().getName())
                        .grade(request.getAuthor().getGrade())
                        .type(request.getAuthor().getType())
                        .build())
                .build();

    }

    @Transactional
    public boolean toggleLike(Long requestId, Long userId) {
        RequestEntity request = repository.findById(requestId)
                .orElseThrow();

        List<Long> likesIds = request.getLikesId();
        synchronized (likesIds) {
            boolean like = likesIds.contains(userId);
            if (like) {
                likesIds.remove(userId);
            } else {
                likesIds.add(userId);
            }
            return !like;
        }
    }
}
