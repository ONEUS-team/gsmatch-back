package oneus.GSMATCH.domain.user.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import static oneus.GSMATCH.global.util.UserStateEnum.*;

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
    private Major major;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;

    @Getter
    private boolean admin = false;
    private String adminToken = "";
}
