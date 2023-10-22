package oneus.GSMATCH.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import static oneus.GSMATCH.global.util.UserStateEnum.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoResponse {
    private Long id;
    private String username;
    private Grade grade;
    private Integer level;
    private Integer point;
    private Major major;
    private Gender gender;
    private Type type;
    private List<RequestsResponse> requestList;
}
