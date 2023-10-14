package oneus.GSMATCH.domain.request.service;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.request.dto.request.CreateRequest;
import oneus.GSMATCH.domain.request.entity.RequestEntity;
import oneus.GSMATCH.domain.request.repository.RequestRepository;
import oneus.GSMATCH.domain.user.entity.UserEntity;
import oneus.GSMATCH.global.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;

    public void saveRequest(CreateRequest createRequest, UserEntity userEntity) {

        // 요청 받을 사람을 구하는 로직

        RequestEntity requestEntity = RequestEntity.builder()
                .title(createRequest.getTitle())
                .content(createRequest.getContent())
                .requestType(createRequest.getRequest_type())
                .requestGrade(createRequest.getRequest_grade())
                .requestGender(createRequest.getRequest_gender())
                .requestMajor(createRequest.getRequest_major())
                .authorId(userEntity)
                .build();

        requestRepository.save(requestEntity);
    }

}
