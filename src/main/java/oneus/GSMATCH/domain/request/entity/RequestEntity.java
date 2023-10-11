package oneus.GSMATCH.domain.request.entity;

import jakarta.persistence.*;
import lombok.*;
import oneus.GSMATCH.domain.user.entity.UserEntity;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity authorId;

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
}

