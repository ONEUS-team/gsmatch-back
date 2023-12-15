package oneus.GSMATCH.domain.request.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static oneus.GSMATCH.global.util.UserStateEnum.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoResponse {
    private Long id;
    private String title;
    private String content;
    private Boolean requestOnly;
    private Author author;
    private List<String> imageNames;
}
