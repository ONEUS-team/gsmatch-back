package oneus.GSMATCH.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oneus.GSMATCH.request.entity.RequestEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long usersId;

    @Column(name = "name")
    private String name;

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

    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
    private List<RequestEntity> users = new ArrayList<>();

}
