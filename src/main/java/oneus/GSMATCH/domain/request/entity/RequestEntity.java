    package oneus.GSMATCH.domain.request.entity;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import jakarta.persistence.*;
    import lombok.*;
    import oneus.GSMATCH.domain.user.entity.UserEntity;
    import static oneus.GSMATCH.global.util.UserStateEnum.*;

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

        @Enumerated(EnumType.STRING)
        @Column(name = "request_grade")
        private Grade requestGrade;

        @Column(name = "request_only")
        private Boolean requestOnly;

        @JoinColumn(name = "author_id")
        @ManyToOne
        @JsonIgnoreProperties({"requestList", "password", "email", "role", "point"})
        private UserEntity author;

        @ElementCollection
        @Column(name = "recipients_id")
        private List<Long> recipientsId;

        @ElementCollection
        @Column(name = "request_major")
        private List<Major> requestMajor;

        @Enumerated(EnumType.STRING)
        @Column(name = "request_type")
        private RequestType requestType;

        @Enumerated(EnumType.STRING)
        @Column(name = "request_gender")
        private Gender requestGender;

        public void setRecipientsId(List<Long> recipientsId) {
            this.recipientsId = recipientsId;
        }

        public void modifyRequest(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }

