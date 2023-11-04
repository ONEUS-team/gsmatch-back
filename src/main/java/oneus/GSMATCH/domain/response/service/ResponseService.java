package oneus.GSMATCH.domain.response.service;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.request.dto.response.Author;
import oneus.GSMATCH.domain.request.dto.response.InfoResponse;
import oneus.GSMATCH.domain.request.entity.RequestEntity;
import oneus.GSMATCH.domain.response.dto.ResponseInfo;
import oneus.GSMATCH.domain.response.repository.RequestEntityRepository;
import oneus.GSMATCH.global.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final RequestEntityRepository repository;

    @Transactional
    public List<ResponseInfo> getRequest (UserDetailsImpl userDetails){
        List<RequestEntity> userRequest = repository.findByRecipientsIdContains(userDetails.getUser().getUsersId());

        return userRequest.stream()
                .map(request -> mapToRequestInfo(request, userDetails.getUser().getUsersId()))
                .collect(Collectors.toList());
    }

    private ResponseInfo mapToRequestInfo (RequestEntity request, Long id) {
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setTitle(request.getTitle());
        responseInfo.setId(request.getRequestId());
        responseInfo.setAuthorName(request.getAuthor().getName());
        responseInfo.setRequestOnly(request.getRequestOnly());
        responseInfo.setLike(request.getLikesId().contains(id));
        responseInfo.setType(request.getRequestType());

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
