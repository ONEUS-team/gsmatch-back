    package oneus.GSMATCH.domain.request.entity;


import jakarta.persistence.*;
import lombok.*;
import oneus.GSMATCH.domain.chat.entity.RoomEntity;
import oneus.GSMATCH.domain.user.entity.UserEntity;
import oneus.GSMATCH.image.entity.ImageEntity;

import java.util.ArrayList;
import java.util.List;
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

        @ElementCollection
        @Column(name = "request_grade")
        private List<Grade> requestGrade;

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
        private List<Gender> requestGender;

        @ElementCollection
        @Column(name = "likes_id")
        private List<Long> likesId;

        @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<RoomEntity> roomEntityList;

        public void setRecipientsId(List<Long> recipientsId) {
            this.recipientsId = recipientsId;
        }

        public void modifyRequest(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public void setRequestOnly(boolean requestOnly) {
            this.requestOnly = requestOnly;
        }

    }

     enum RequestGender {
        MALE,
        FEMALE;
    }

/*
    @OneToMany(mappedBy = "requestId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageEntity> requestImagesList;
*/
