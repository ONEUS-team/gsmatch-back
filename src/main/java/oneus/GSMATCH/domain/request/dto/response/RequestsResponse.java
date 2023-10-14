package oneus.GSMATCH.domain.request.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oneus.GSMATCH.global.util.UserStateEnum;

import java.util.List;
import static oneus.GSMATCH.global.util.UserStateEnum.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestsResponse {
    private String title;
    private String content;
    private RequestType request_type;
    private String author_name;
}
