package oneus.GSMATCH.domain.request.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oneus.GSMATCH.global.util.UserStateEnum;

import java.util.List;

@Getter
@NoArgsConstructor
public class RangeRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private UserStateEnum.Type request_type;
    @NotBlank
    private UserStateEnum.Grade request_grade;
    @NotBlank
    private UserStateEnum.Gender request_gender;
    @NotBlank
    private List<UserStateEnum.Major> request_major;
}
