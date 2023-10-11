package oneus.GSMATCH.domain.user.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class SignupRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private List<Major> major;

    @Getter
    private boolean admin = false;
    private String adminToken = "";


    private enum Grade {
        ONE,
        TWO,
        THREE;
    }

    private enum Gender {
        MALE,
        FEMALE;
    }

    private enum Major {
        FRONT,
        BACK,
        DESIGN,
        IOS,
        AOS,
        DEVOPS,
        WORLD_SKILL,
        IOT,
        AI;
    }
}
