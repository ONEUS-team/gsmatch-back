package oneus.GSMATCH.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignOutRequest {
    private String username;
    private String password;
}
