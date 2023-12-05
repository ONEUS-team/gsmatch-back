package oneus.GSMATCH.image.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.image.dto.ImageRequestDto;
import oneus.GSMATCH.image.dto.ImageResponseDto;
import oneus.GSMATCH.image.repository.ImageRepository;
import oneus.GSMATCH.image.entity.ImageEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final
    ImageRepository imageRepository;

    @Transactional
    public ImageResponseDto saveImage(MultipartFile image) throws IOException {

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
                .build();

        ImageEntity savedEntity = imageRepository.save(imageDto.toEntity());
        Long savedImageId = savedEntity.getImageId();

        return new ImageResponseDto(savedImageId);
    }

}
