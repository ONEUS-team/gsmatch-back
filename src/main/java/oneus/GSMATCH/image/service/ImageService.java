package oneus.GSMATCH.image.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.image.dto.ImageDto;
import oneus.GSMATCH.image.repository.ImageRepository;
import org.springframework.stereotype.Service;



@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional
    public Long saveImage(ImageDto imageDto) {
        return imageRepository.save(imageDto.toEntity()).getImageId();
    }
}
