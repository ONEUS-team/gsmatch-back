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
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private Long userId;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<RequestEntity> members = new ArrayList<>();

}
