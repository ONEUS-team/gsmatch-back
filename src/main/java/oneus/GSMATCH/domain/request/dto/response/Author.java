package oneus.GSMATCH.domain.request.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import static oneus.GSMATCH.global.util.UserStateEnum.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {
    private String name;
    private Grade grade;
    private Type type;
    private Integer level;
}