package oneus.GSMATCH.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class SignupRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Getter
    private boolean admin = false;
    private String adminToken = "";
}
