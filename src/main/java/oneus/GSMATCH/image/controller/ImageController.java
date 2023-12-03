package oneus.GSMATCH.image.controller;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.global.exception.CustomException;
import oneus.GSMATCH.global.exception.ErrorCode;
import oneus.GSMATCH.image.dto.ImageRequestDto;
import oneus.GSMATCH.image.dto.ImageResponseDto;
import oneus.GSMATCH.image.service.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static oneus.GSMATCH.global.exception.ErrorCode.INVALID_IMAGE_EXTENSION;

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
