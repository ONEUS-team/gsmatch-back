package oneus.GSMATCH.domain.request.service;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.image.dto.ImageRequestDto;
import oneus.GSMATCH.domain.image.entity.ImageEntity;
import oneus.GSMATCH.domain.image.repository.ImageRepository;
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
import oneus.GSMATCH.global.exception.ErrorCode;
import oneus.GSMATCH.global.level.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static oneus.GSMATCH.global.exception.ErrorCode.*;
import static oneus.GSMATCH.global.util.UserStateEnum.*;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final Point point;

    // 이미지 포함한 요청 저장
    @Transactional
    public void saveRequest(RequestRequest createRequest, UserEntity userEntity, List<MultipartFile> images) throws IOException {
        ableSendRequest(userEntity);
        RequestEntity requestEntity = createRequest.toEntity(userEntity);

        List<Long> recipientsList = findRecipientsId(createRequest, userEntity.getType(), userEntity.getUsersId());

        // 특수요청
        if (createRequest.getIsOnlyone() != null && createRequest.getIsOnlyone()) {
            Long recipient = isOnlyOne(createRequest, userEntity.getType(), userEntity.getUsersId());
            requestEntity.setRecipientsId(List.of(recipient));
            requestEntity.setRequestOnly(true);
            RequestEntity request = requestRepository.save(requestEntity);

            saveImage(images, request);
            point.requestPoint(userEntity.getUsersId());
        }
        // 일반요청
        else {
            if (recipientsList.isEmpty())
                throw new CustomException(DONT_SEND_REQUEST);

            requestEntity.setRecipientsId(recipientsList);
            requestEntity.setRequestOnly(false);
            RequestEntity request = requestRepository.save(requestEntity);

            saveImage(images, request);
            point.requestPoint(userEntity.getUsersId());
        }
    }

    //이미지 포함하지 않은 요청 저장
    @Transactional
    public void saveRequestWithoutImages(RequestRequest createRequest, UserEntity userEntity) {
        ableSendRequest(userEntity);
        RequestEntity requestEntity = createRequest.toEntity(userEntity);

        List<Long> recipientsList = findRecipientsId(createRequest, userEntity.getType(), userEntity.getUsersId());

        if (createRequest.getIsOnlyone() != null && createRequest.getIsOnlyone()) {
            Long recipient = isOnlyOne(createRequest, userEntity.getType(), userEntity.getUsersId());
            requestEntity.setRecipientsId(List.of(recipient));
            requestEntity.setRequestOnly(true);
            RequestEntity request = requestRepository.save(requestEntity);

            point.requestPoint(userEntity.getUsersId());
        }
        // 일반요청
        else {
            if (recipientsList.isEmpty())
                throw new CustomException(DONT_SEND_REQUEST);

            requestEntity.setRecipientsId(recipientsList);
            requestEntity.setRequestOnly(false);
            RequestEntity request = requestRepository.save(requestEntity);
            point.requestPoint(userEntity.getUsersId());
        }
    }

    @Transactional(readOnly = true)
    public RangeResponse rangeRequest(RequestRequest requestRequest, UserEntity userEntity) {
        ableSendRequest(userEntity);
        List<Long> recipientsList = findRecipientsId(requestRequest, userEntity.getType(), userEntity.getUsersId());
        return new RangeResponse(recipientsList.size());
    }


    @Transactional
    public InfoResponse infoRequest(Long requestId) {
        RequestEntity request = requestRepository.findById(requestId)
                .orElseThrow(() -> new CustomException(NOT_OK_REQUEST));

        List<Long> requestImagesIds = Collections.emptyList();
        List<String> imageNames = Collections.emptyList();

        if (request.getRequestImagesList() != null) {
            requestImagesIds = request.getRequestImagesList().stream()
                    .map(ImageEntity::getImageId)
                    .collect(Collectors.toList());

            imageNames = request.getRequestImagesList().stream()
                    .map(imageEntity -> "/images/" + imageEntity.getImageName())
                    .collect(Collectors.toList());
        }

        InfoResponse response = InfoResponse.builder()
                .id(request.getRequestId())
                .title(request.getTitle())
                .content(request.getContent())
                .requestOnly(request.getRequestOnly())
                .author(Author.builder()
                        .name(request.getAuthor().getName())
                        .grade(request.getAuthor().getGrade())
                        .type(request.getAuthor().getType())
                        .level(request.getAuthor().getLevel())
                        .build())
                .imageNames(imageNames)
                .build();

        return response;
    }

    @Transactional
    public void modifyRequest(Long requestId, ModifyRequest request, UserEntity user) {
        if (isNotAccessRequest(user, requestId))
            throw new CustomException(NOT_OK_REQUEST);

        RequestEntity requestEntity = requestRepository.findById(requestId)
                .orElseThrow(() -> new CustomException(NOT_OK_REQUEST));
        requestEntity.modifyRequest(request.getTitle(), request.getContent());

        requestRepository.save(requestEntity);
    }

    @Transactional
    public void deleteRequest(Long requestId, UserEntity user) {
        if (isNotAccessRequest(user, requestId))
            throw new CustomException(NOT_OK_REQUEST);

        requestRepository.deleteByRequestId(requestId);
    }


    // 요청이 3개 이상인지 검증
    private void ableSendRequest(UserEntity user) {
        if (requestRepository.countByAuthor(user) >= 3) {
            throw new CustomException(MANY_REQUEST);
        }
    }

    // 요청을 받는 사용자 특정
    private List<Long> findRecipientsId(RequestRequest request, Type userType, Long userId) {

        List<Long> userIdList;

        if (request.getRequestType().equals(RequestType.TYPE)) {
            userIdList = userRepository.findByGradeInAndTypeAndGenderInAndUsersIdNot(request.getRequestGrades(), userType, request.getRequestGenders(), userId).stream()
                    .map(UserEntity::getUsersId).toList();
        } else if (request.getRequestType().equals(RequestType.STUDY)) {
            userIdList = userRepository.findByGradeInAndMajorInAndUsersIdNot(request.getRequestGrades(), request.getRequestMajors(), userId).stream()
                    .map(UserEntity::getUsersId).toList();
        } else {
            throw new CustomException(NOT_OK_REQUEST);
        }

        return userIdList;
    }

    // 조건에 부합하는 단 한명의 유저 아이디 반환
    private Long isOnlyOne(RequestRequest request, Type userType, Long userId) {
        List<Long> userIdList = userRepository.findByGradeInAndTypeAndUsersIdNot(request.getRequestGrades(), userType, userId)
                .stream()
                .map(UserEntity::getUsersId)
                .toList();

        if (userIdList.isEmpty())
            throw new CustomException(DONT_SEND_REQUEST);

        Random random = new Random();
        return userIdList.get(random.nextInt(userIdList.size()));
    }

    // 존재하지 않는 요청인지 검증 + 해당 사용자가 보내지 않은 요청인지 검증
    private boolean isNotAccessRequest(UserEntity user, Long requestId) {
        boolean isNotUserSendRequest = user.getRequestList().stream()
                .noneMatch(requestEntity -> Objects.equals(requestEntity.getRequestId(), requestId));

        return !requestRepository.existsByRequestId(requestId) || isNotUserSendRequest;
    }

    // 이미지 저장
    private List<Long> saveImage(List<MultipartFile> images, RequestEntity request) throws IOException {
        List<Long> savedImageIds = new ArrayList<>();

        for (MultipartFile image : images) {
            if(image != null &&
                    !image.getOriginalFilename().toLowerCase().endsWith(".png") &&
                    !image.getOriginalFilename().toLowerCase().endsWith(".jpg") &&
                    !image.getOriginalFilename().toLowerCase().endsWith(".jpeg")){
                throw new CustomException(ErrorCode.INVALID_IMAGE_EXTENSION);
            }

            String fileUUID = UUID.randomUUID().toString();

            File file = new File(System.getProperty("user.dir") + File.separator + "/src/main/resources/static/images/");
            if (!file.exists()) {
                file.mkdirs();
            }

            String fileName = fileUUID + "_" + image.getOriginalFilename();
            file = new File(file.getPath() + File.separator + fileName);

            // file 경로에 image 객체를 저장!
            image.transferTo(file);

            ImageRequestDto imageDto = ImageRequestDto.builder()
                    .originImageName(image.getOriginalFilename())
                    .imagePath(file.getPath())
                    .imageName(fileName)
                    .request(request)
                    .build();

            ImageEntity savedEntity = imageRepository.save(imageDto.toEntity());
            savedImageIds.add(savedEntity.getImageId());
        }

        return savedImageIds;
    }
}
