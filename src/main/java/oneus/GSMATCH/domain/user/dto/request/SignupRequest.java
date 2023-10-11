package oneus.GSMATCH.domain.user.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Enumerated(EnumType.STRING)
    private List<Major> major;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;

    @Getter
    private boolean admin = false;
    private String adminToken = "";


    public enum Grade {
        ONE,
        TWO,
        THREE;
    }

    public enum Gender {
        MALE,
        FEMALE;
    }

    public enum Major {
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

    public enum Type {
        PORORO,
        LUPI,
        POBI,
        EDI;
    }
}
