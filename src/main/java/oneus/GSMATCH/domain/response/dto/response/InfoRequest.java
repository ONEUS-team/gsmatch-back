package oneus.GSMATCH.domain.response.dto.response;

import lombok.*;
import oneus.GSMATCH.domain.request.dto.response.Author;
import oneus.GSMATCH.global.util.UserStateEnum.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoRequest {
    private Long responseId;
    private String title;
    private String content;
    private Boolean requestOnly;
    private Author author;
    private RequestType requestType;
    private Boolean likes;
}
