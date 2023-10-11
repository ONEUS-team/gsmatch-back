package oneus.GSMATCH.request.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oneus.GSMATCH.user.entity.UsersEntity;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "request")
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "request_grade")
    private Integer requestGrade;

    @Column(name = "request_only")
    private Boolean requestOnly;

    @Column(name = "author_id")
    private Long authorId;

    @ElementCollection
    @Column(name = "recipients_id")
    private List<Long> recipientsId;

    @ElementCollection
    @Column(name = "request_major")
    private List<String> requestMajor;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_type")
    private RequestType requestType;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_gender")
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
    @JoinColumn(name = "users_id")
    private UsersEntity userId;

}

