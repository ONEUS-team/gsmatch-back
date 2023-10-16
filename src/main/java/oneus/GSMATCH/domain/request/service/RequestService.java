package oneus.GSMATCH.domain.request.service;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.request.dto.request.CreateRequest;
import oneus.GSMATCH.domain.request.entity.RequestEntity;
import oneus.GSMATCH.domain.request.repository.RequestRepository;
import oneus.GSMATCH.domain.user.entity.UserEntity;
import oneus.GSMATCH.domain.user.repository.UserRepository;
import oneus.GSMATCH.global.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static oneus.GSMATCH.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;

    @Transactional
    public void saveRequest(CreateRequest createRequest, UserEntity userEntity) {

        ableSendRequest(userEntity);

        // 요청 받을 사람을 구하는 로직

        RequestEntity requestEntity = RequestEntity.builder()
                .title(createRequest.getTitle())
                .content(createRequest.getContent())
                .requestType(createRequest.getRequest_type())
                .requestGrade(createRequest.getRequest_grade())
                .requestGender(createRequest.getRequest_gender())
                .requestMajor(createRequest.getRequest_major())
                .author(userEntity)
                .build();


        requestRepository.save(requestEntity);
    }

    @Transactional
    public void deleteRequest(Long requestId, UserEntity user) {

        // 존재하는 요청인지 검증
        if (!requestRepository.existsByRequestId(requestId))
            throw new CustomException(NOT_OK_REQUEST);

        // 해당 사용자가 보낸 요청인지 검증
        if (user.getRequestList().stream()
                .noneMatch(requestEntity -> Objects.equals(requestEntity.getRequestId(), requestId)))
            throw new CustomException(NOT_OK_REQUEST);

        requestRepository.deleteByRequestId(requestId);
    }


    // 요청이 3개 이상인지 검증
    public void ableSendRequest(UserEntity user) {
        if (requestRepository.findByAuthor(user)
                .orElseThrow(() -> new CustomException(NOT_MATCH_INFORMATION))
                .size() >= 3) {
            throw new CustomException(MANY_REQUEST);
        }
    }

}
