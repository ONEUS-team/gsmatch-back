package oneus.GSMATCH.domain.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import static oneus.GSMATCH.global.util.UserStateEnum.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Partner {
    private Long id;
    private String name;
    private Major major;
    private Grade grade;
    private Type type;
}