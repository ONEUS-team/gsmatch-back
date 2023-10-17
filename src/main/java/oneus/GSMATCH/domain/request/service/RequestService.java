package oneus.GSMATCH.domain.request.service;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.request.dto.request.ModifyRequest;
import oneus.GSMATCH.domain.request.dto.request.RequestRequest;
import oneus.GSMATCH.domain.request.dto.response.Author;
import oneus.GSMATCH.domain.request.dto.response.InfoResponse;
import oneus.GSMATCH.domain.request.dto.response.RangeResponse;
import oneus.GSMATCH.domain.request.entity.RequestEntity;
import oneus.GSMATCH.domain.request.repository.RequestRepository;
import oneus.GSMATCH.domain.user.entity.UserEntity;
import oneus.GSMATCH.domain.user.repository.UserRepository;
import oneus.GSMATCH.global.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import static oneus.GSMATCH.global.exception.ErrorCode.*;
import static oneus.GSMATCH.global.util.UserStateEnum.*;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveRequest(RequestRequest createRequest, UserEntity userEntity) {
        ableSendRequest(userEntity);
        RequestEntity requestEntity = RequestEntity.builder()
                .title(createRequest.getTitle())
                .content(createRequest.getContent())
                .requestType(createRequest.getRequest_type())
                .requestGrade(createRequest.getRequest_grade())
                .requestGender(createRequest.getRequest_gender())
                .requestMajor(createRequest.getRequest_major())
                .author(userEntity)
                .build();

        List<Long> recipientsList = findRecipientsId(createRequest, userEntity.getType(), userEntity.getUsersId());

        // 특수요청
        if (createRequest.getIs_onlyone() != null && createRequest.getIs_onlyone()) {
            Long recipient = isOnlyOne(createRequest, userEntity.getType(), userEntity.getUsersId());
            requestEntity.setRecipientsId(List.of(recipient));
            requestEntity.setRequestOnly(true);
            requestRepository.save(requestEntity);
        }
        // 일반요청
        else {
            if (recipientsList.isEmpty())
                throw new CustomException(DONT_SEND_REQUEST);

            requestEntity.setRecipientsId(recipientsList);
            requestEntity.setRequestOnly(false);
            requestRepository.save(requestEntity);
        }
    }

    @Transactional
    public RangeResponse rangeRequest(RequestRequest requestRequest, UserEntity userEntity) {
        ableSendRequest(userEntity);
        List<Long> recipientsList = findRecipientsId(requestRequest, userEntity.getType(), userEntity.getUsersId());
        return new RangeResponse(recipientsList.size());
    }


    @Transactional
    public InfoResponse infoRequest(Long requestId) {
        RequestEntity request = requestRepository.findById(requestId)
                .orElseThrow(() -> new CustomException(NOT_OK_REQUEST));
        return InfoResponse.builder()
                .id(request.getRequestId())
                .title(request.getTitle())
                .content(request.getContent())
                .request_only(request.getRequestOnly())
                .author(Author.builder()
                        .name(request.getAuthor().getName())
                        .grade(request.getAuthor().getGrade())
                        .type(request.getAuthor().getType())
                        .level(request.getAuthor().getLevel())
                        .build())
                .build();
    }

    @Transactional
    public void modifyRequest(Long requestId, ModifyRequest request, UserEntity user) {

        // 존재하는 요청인지 검증 + 해당 사용자가 보낸 요청인지 검증
        if (!requestRepository.existsByRequestId(requestId)
                &&
                user.getRequestList().stream()
                        .noneMatch(requestEntity -> Objects.equals(requestEntity.getRequestId(), requestId)))
            throw new CustomException(NOT_OK_REQUEST);

        RequestEntity requestEntity = requestRepository.findById(requestId)
                .orElseThrow(() -> new CustomException(NOT_OK_REQUEST));
        requestEntity.modifyRequest(request.getTitle(), request.getContent());

        requestRepository.save(requestEntity);
    }

    @Transactional
    public void deleteRequest(Long requestId, UserEntity user) {

        if (!requestRepository.existsByRequestId(requestId)
                &&
                user.getRequestList().stream()
                        .noneMatch(requestEntity -> Objects.equals(requestEntity.getRequestId(), requestId)))
            throw new CustomException(NOT_OK_REQUEST);

        requestRepository.deleteByRequestId(requestId);
    }


    // 요청이 3개 이상인지 검증
    private void ableSendRequest(UserEntity user) {
        if (requestRepository.findByAuthor(user)
                .orElseThrow(() -> new CustomException(NOT_MATCH_INFORMATION))
                .size() >= 3) {
            throw new CustomException(MANY_REQUEST);
        }
    }

    // 요청을 받는 사용자 특정
    private List<Long> findRecipientsId(RequestRequest request, Type userType, Long userId) {

        List<Long> userIdList;

        if (request.getRequest_type().equals(RequestType.TYPE)) {
            userIdList = userRepository.findByGradeInAndTypeAndGenderInAndUsersIdNot(request.getRequest_grade(), userType, request.getRequest_gender(), userId).stream()
                    .map(UserEntity::getUsersId).toList();
        } else if (request.getRequest_type().equals(RequestType.STUDY)) {
            userIdList = userRepository.findByGradeInAndMajorInAndUsersIdNot(request.getRequest_grade(), request.getRequest_major(), userId).stream()
                    .map(UserEntity::getUsersId).toList();
        } else {
            throw new CustomException(NOT_OK_REQUEST);
        }

        return userIdList;
    }

    private Long isOnlyOne(RequestRequest request, Type userType, Long userId) {
        List<Long> userIdList = userRepository.findByGradeInAndTypeAndUsersIdNot(request.getRequest_grade(), userType, userId)
                    .stream()
                    .map(UserEntity::getUsersId)
                    .toList();

        if (userIdList.isEmpty())
            throw new CustomException(DONT_SEND_REQUEST);

        Random random = new Random();
        return userIdList.get(random.nextInt(userIdList.size()));
    }
}
