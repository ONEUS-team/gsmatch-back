package oneus.GSMATCH.image.controller;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.image.dto.ImageRequestDto;
import oneus.GSMATCH.image.dto.ImageResponseDto;
import oneus.GSMATCH.image.service.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/image")
    public ImageResponseDto imageUpload(@RequestParam(name = "image") MultipartFile image) throws IOException{

        String fileUUID = UUID.randomUUID().toString();

        if (!image.isEmpty()) {
            File file = new File(System.getProperty("user.dir") + File.separator + "/src/main/resources/images/");

            if (!file.exists()) {
                file.mkdirs();
            }

            String fileName = fileUUID + "_" + image.getOriginalFilename();
            file = new File(file.getPath() + File.separator + fileName);

            image.transferTo(file);

            ImageRequestDto imageDto = ImageRequestDto.builder()
                    .originImageName(image.getOriginalFilename())
                    .imagePath(file.getPath())
                    .imageName(fileName)
                    .build();
            return imageService.saveImage(imageDto);
        }

        return null;
    }
}
