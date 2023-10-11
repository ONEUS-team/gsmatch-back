package oneus.GSMATCH.request.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oneus.GSMATCH.user.entity.UserEntity;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "request")
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requestId")
    private Long requestId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "requestGrade")
    private Integer requestGrade;

    @Column(name = "requestOnly")
    private Boolean requestOnly;

    @Column(name = "authorId")
    private Long authorId;

    @ElementCollection
    @Column(name = "recipientsId")
    private List<Long> recipientsId;

    @ElementCollection
    @Column(name = "requestMajor")
    private List<String> requestMajor;

    @Enumerated(EnumType.STRING)
    @Column(name = "requestType")
    private RequestType requestType;

    @Enumerated(EnumType.STRING)
    @Column(name = "requestGender")
    private RequestGender requestGender;

    private enum RequestType {
        TYPE,
        STUDY;
    }

    private enum RequestGender {
        MALE,
        FEMALE;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserEntity user;

}

