package oneus.GSMATCH.domain.image.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oneus.GSMATCH.domain.image.entity.ImageEntity;
import oneus.GSMATCH.domain.request.entity.RequestEntity;

@Getter
@NoArgsConstructor
public class ImageRequestDto {
    private String originImageName;
    private String imageName;
    private String imagePath;
    private RequestEntity request;

    public ImageEntity toEntity() {
        ImageEntity imageEntity = ImageEntity.builder()
                .originImageName(originImageName)
                .imageName(imageName)
                .imagePath(imagePath)
                .requestId(request)
                .build();
        return imageEntity;
    }

    @Builder
    public ImageRequestDto(String originImageName, String imageName, String imagePath, RequestEntity request) {
        this.originImageName = originImageName;
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.request = request;
    }
}
