package oneus.GSMATCH.domain.request.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import static oneus.GSMATCH.global.util.UserStateEnum.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class RangeRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    @Enumerated(EnumType.STRING)
    private RequestType request_type;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Grade request_grade;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender request_gender;
    @NotNull
    @Enumerated(EnumType.STRING)
    private List<Major> request_major;
}
