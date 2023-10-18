package oneus.GSMATCH.domain.request.dto.request;

import static oneus.GSMATCH.global.util.UserStateEnum.*;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oneus.GSMATCH.domain.request.entity.RequestEntity;
import oneus.GSMATCH.domain.user.entity.UserEntity;

import java.util.List;

@Getter
@NoArgsConstructor
public class RequestRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private List<Grade> requestGrade;

    @Enumerated(EnumType.STRING)
    private List<Gender> requestGender;

    @Enumerated(EnumType.STRING)
    private List<Major> requestMajor;

    private Boolean isOnlyone;

    public RequestEntity toEntity(UserEntity author) {
        return RequestEntity.builder()
                .title(title)
                .content(content)
                .requestType(requestType)
                .requestGrade(requestGrade)
                .requestGender(requestGender)
                .requestMajor(requestMajor)
                .author(author)
                .build();
    }
}
