package oneus.GSMATCH.request.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "request")
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private Integer request_grade;
    private Boolean request_only;
    private Long author_id;

    @ElementCollection
    private List<Long> recipients_id;

    @ElementCollection
    private List<String> request_major;

    @Enumerated(EnumType.STRING)
    private RequestType RequestType;

    @Enumerated(EnumType.STRING)
    private RequestGender RequestGender;

    private enum RequestType {
        TYPE,
        STUDY;
    }

    private enum RequestGender {
        MALE,
        FEMALE;
    }

}
