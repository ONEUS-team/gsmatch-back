package oneus.GSMATCH.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignOutRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}