package oneus.GSMATCH.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import oneus.GSMATCH.domain.request.entity.RequestEntity;
import oneus.GSMATCH.global.util.UserRoleEnum;

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
    private Integer grade;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "level")
    private Integer level;

    @Column(name = "point")
    private Integer point;

    @ElementCollection
    @Column(name = "major")
    private List<String> major;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    private enum Gender {
        MAN,
        WOMAN;
    }

    private enum Type {
        PORORO,
        LUPI,
        POBI,
        EDI;
    }

    @OneToMany(mappedBy = "authorId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequestEntity> requestList = new ArrayList<>();

}
