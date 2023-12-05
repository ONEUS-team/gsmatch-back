package oneus.GSMATCH.image.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oneus.GSMATCH.image.entity.ImageEntity;

@Getter
@NoArgsConstructor
public class ImageRequestDto {
    private String originImageName;
    private String imageName;
    private String imagePath;

    public ImageEntity toEntity() {
        ImageEntity imageEntity = ImageEntity.builder()
                .originImageName(originImageName)
                .imageName(imageName)
                .imagePath(imagePath)
                .build();
        return imageEntity;
    }

    @Builder
    public ImageRequestDto(String originImageName, String imageName, String imagePath) {
        this.originImageName = originImageName;
        this.imageName = imageName;
        this.imagePath = imagePath;
    }
}
