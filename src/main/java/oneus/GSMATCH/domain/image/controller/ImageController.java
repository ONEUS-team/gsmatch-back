package oneus.GSMATCH.domain.image.controller;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.image.service.ImageService;
import oneus.GSMATCH.global.exception.CustomException;
import oneus.GSMATCH.global.exception.ErrorCode;
import oneus.GSMATCH.domain.image.dto.ImageResponseDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/image")
    public ImageResponseDto imageUpload(@RequestParam(name = "image") MultipartFile image) throws IOException{
        validateImageExtension(image);
        return imageService.saveImage(image);
    }

    private void validateImageExtension(MultipartFile image) {
        String imageOriginName = image.getOriginalFilename();
        if(imageOriginName != null &&
                !imageOriginName.toLowerCase().endsWith(".png") &&
                !imageOriginName.toLowerCase().endsWith(".jpg") &&
                !imageOriginName.toLowerCase().endsWith(".jpeg")){
            throw new CustomException(ErrorCode.INVALID_IMAGE_EXTENSION);
        }
    }
}
