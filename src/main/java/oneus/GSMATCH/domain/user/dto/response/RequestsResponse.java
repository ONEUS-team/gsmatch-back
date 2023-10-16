package oneus.GSMATCH.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static oneus.GSMATCH.global.util.UserStateEnum.RequestType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestsResponse {
    private Long request_id;
    private String title;
    private String content;
    private RequestType request_type;
    private String author_name;
}
