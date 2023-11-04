package oneus.GSMATCH.domain.response.dto;

import lombok.*;

import static oneus.GSMATCH.global.util.UserStateEnum.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class ResponseInfo {
    private Long id;
    private String authorName;
    private RequestType type;
    private Boolean liked;
    private Boolean requestOnly;
    private String title;

}
