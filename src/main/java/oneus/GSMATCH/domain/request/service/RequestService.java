package oneus.GSMATCH.domain.request.service;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.request.dto.request.CreateRequest;
import oneus.GSMATCH.domain.request.dto.response.RequestsResponse;
import oneus.GSMATCH.domain.request.entity.RequestEntity;
import oneus.GSMATCH.domain.request.repository.RequestRepository;
import oneus.GSMATCH.domain.user.entity.UserEntity;
import oneus.GSMATCH.domain.user.repository.UserRepository;
import oneus.GSMATCH.global.exception.CustomException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static oneus.GSMATCH.global.exception.ErrorCode.MANY_REQUEST;
import static oneus.GSMATCH.global.exception.ErrorCode.NOT_MATCH_INFORMATION;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;


    public List<RequestsResponse> findRequests(UserEntity user) {
        return requestRepository.findByAuthor(user)
                .orElseThrow(() -> new CustomException(NOT_MATCH_INFORMATION))
                .stream()
                .map(item -> RequestsResponse.builder()
                        .title(item.getTitle())
                        .content(item.getContent())
                        .request_type(item.getRequestType())
                        .author_name(item.getAuthor().getName())
                        .build())
                .collect(Collectors.toList());
    }

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

//        UserEntity newUser = userRepository.findById(userEntity.getUsersId()).orElseThrow(() -> new CustomException(NOT_MATCH_INFORMATION));
//        newUser.setRequestList(requestRepository.findByAuthorId(newUser).orElseThrow(() -> new CustomException(NOT_MATCH_INFORMATION)));
//        userRepository.save(newUser);
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
