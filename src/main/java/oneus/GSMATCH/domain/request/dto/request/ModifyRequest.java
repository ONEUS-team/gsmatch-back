package oneus.GSMATCH.domain.request.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ModifyRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
