package oneus.GSMATCH.image.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.image.dto.ImageRequestDto;
import oneus.GSMATCH.image.dto.ImageResponseDto;
import oneus.GSMATCH.image.repository.ImageRepository;
import oneus.GSMATCH.image.entity.ImageEntity;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final
    ImageRepository imageRepository;

    @Transactional
    public ImageResponseDto saveImage(ImageRequestDto imageRequestDto) {
        ImageEntity savedEntity = imageRepository.save(imageRequestDto.toEntity());
        Long savedImageId = savedEntity.getImageId();

        return new ImageResponseDto(savedImageId);
    }

}
