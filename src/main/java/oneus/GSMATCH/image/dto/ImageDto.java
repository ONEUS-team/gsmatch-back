package oneus.GSMATCH.image.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oneus.GSMATCH.image.ImageEntity;

@Getter
@NoArgsConstructor
public class ImageDto {

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
    public ImageDto (String originImageName, String imageName,String imagePath) {
        this.originImageName = originImageName;
        this.imageName = imageName;
        this.imagePath = imagePath;
    }
}
