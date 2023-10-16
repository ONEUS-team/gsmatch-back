package oneus.GSMATCH.domain.request.dto.request;

import static oneus.GSMATCH.global.util.UserStateEnum.*;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RequestRequest {
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

    @Enumerated(EnumType.STRING)
    private Gender request_gender;

    @Enumerated(EnumType.STRING)
    private List<Major> request_major;

    private Boolean is_onlyone;
}
