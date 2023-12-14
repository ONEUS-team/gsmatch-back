package oneus.GSMATCH.domain.image.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.image.dto.ImageRequestDto;
import oneus.GSMATCH.domain.image.dto.ImageResponseDto;
import oneus.GSMATCH.domain.image.repository.ImageRepository;
import oneus.GSMATCH.domain.image.entity.ImageEntity;

import oneus.GSMATCH.domain.request.entity.RequestEntity;
import oneus.GSMATCH.global.exception.CustomException;
import oneus.GSMATCH.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional
    public List<Long> saveImage(List<MultipartFile> images, RequestEntity request) throws IOException {
        List<Long> savedImageIds = new ArrayList<>();

        for (MultipartFile image : images) {
            if(image != null &&
                    !image.getOriginalFilename().toLowerCase().endsWith(".png") &&
                    !image.getOriginalFilename().toLowerCase().endsWith(".jpg") &&
                    !image.getOriginalFilename().toLowerCase().endsWith(".jpeg")){
                throw new CustomException(ErrorCode.INVALID_IMAGE_EXTENSION);
            }

            String fileUUID = UUID.randomUUID().toString();

            File file = new File(System.getProperty("user.dir") + File.separator + "/src/main/resources/images/");
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
