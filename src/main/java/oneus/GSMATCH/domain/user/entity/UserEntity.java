package oneus.GSMATCH.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import oneus.GSMATCH.domain.request.entity.RequestEntity;
import oneus.GSMATCH.global.util.UserRoleEnum;

import static oneus.GSMATCH.global.util.UserStateEnum.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long usersId;

    @Column(name = "name")
    private String name;

    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column(name = "grade")
    @Enumerated(value = EnumType.STRING)
    private Grade grade;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "level")
    private Integer level;

    @Column(name = "point")
    private Integer point;

    @Enumerated(EnumType.STRING)
    @Column(name = "major")
    private Major major;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<RequestEntity> requestList = new ArrayList<>();

    public void modifyType(Type type) {
        this.type = type;
    }
}
