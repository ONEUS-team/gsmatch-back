package oneus.GSMATCH.domain.request.dto.request;

import static oneus.GSMATCH.global.util.UserStateEnum.*;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreateRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private Type request_type;
    @NotBlank
    private Grade request_grade;
    @NotBlank
    private Gender request_gender;
    @NotBlank
    private List<Major> request_major;
}
