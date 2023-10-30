package oneus.GSMATCH.image.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ImageResponseDto {
    private Long id;

    @Builder
    public ImageResponseDto(Long id) {
        this.id = id;
    }

}
