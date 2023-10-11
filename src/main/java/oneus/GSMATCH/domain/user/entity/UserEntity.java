package oneus.GSMATCH.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import oneus.GSMATCH.domain.request.entity.RequestEntity;
import oneus.GSMATCH.domain.user.dto.request.SignupRequest;
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
    @Enumerated(value = EnumType.STRING)
    private SignupRequest.Grade grade;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "level")
    private Integer level;

    @Column(name = "point")
    private Integer point;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "major")
    private List<SignupRequest.Major> major;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private SignupRequest.Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private SignupRequest.Type type;

    @OneToMany(mappedBy = "authorId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequestEntity> requestList = new ArrayList<>();

}
