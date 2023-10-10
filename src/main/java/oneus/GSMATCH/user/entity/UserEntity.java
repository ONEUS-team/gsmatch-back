package oneus.GSMATCH.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oneus.GSMATCH.request.entity.RequestEntity;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer grade;
    private String password;
    private String email;
    private Integer level;
    private Integer point;

    @ElementCollection
    private List<String> Major;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
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


}
