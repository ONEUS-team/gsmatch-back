package oneus.GSMATCH.domain.response.dto.response;

import lombok.*;

import java.util.List;

import static oneus.GSMATCH.global.util.UserStateEnum.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseInfo {
    private Long responseId;
    private String authorName;
    private RequestType requestType;
    private Boolean likes;
    private Boolean requestOnly;
    private String title;
    private String content;
}
