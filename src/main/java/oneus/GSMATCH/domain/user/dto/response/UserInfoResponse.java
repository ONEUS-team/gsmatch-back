package oneus.GSMATCH.domain.user.dto.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oneus.GSMATCH.domain.request.entity.RequestEntity;
import oneus.GSMATCH.global.util.UserRoleEnum;
import static oneus.GSMATCH.global.util.UserStateEnum.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoResponse {
    private String name;
    private Grade grade;
    private Integer level;
    private Integer point;
    private List<Major> major;
    private Gender gender;
    private Type type;
    private List<RequestEntity> requestList;
}
