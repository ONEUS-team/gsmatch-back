package oneus.GSMATCH.domain.request.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoImageResponse {
    private Long id;
    private String title;
    private String content;
    private Boolean requestOnly;
    private Author author;
    private List<Long> imageIdList;

}
