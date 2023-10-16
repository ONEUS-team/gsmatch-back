package oneus.GSMATCH.image.controller;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.image.dto.ImageDto;
import oneus.GSMATCH.image.service.ImageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/image/get")
    public String imageUpload(@RequestParam(name = "image") MultipartFile image) throws IOException {

        String imagePath = "";

        String newFileName = UUID.randomUUID().toString();
        String path = "images/test/"; // 저장될 폴더 경로

            if (!image.isEmpty()) {
                File file = new File(imagePath + path);


                file = new File(imagePath + path + "/" + newFileName);
                image.transferTo(file);

                ImageDto imageDto = ImageDto.builder()
                        .originImageName(image.getOriginalFilename())
                        .imagePath(path)
                        .imageName(newFileName)
                        .build();

                imageService.saveImage(imageDto);
            }

        return "test/imageV1";
    }
}
