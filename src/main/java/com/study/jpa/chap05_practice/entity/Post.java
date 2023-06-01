package com.study.jpa.chap05_practice.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter
@ToString(exclude = {"hashTags"})
@EqualsAndHashCode (of = {"id"}) // pk넣으면 됨
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_no")
    private Long id;

    @Column (nullable = false)
    private String writer; // 작성자

    @Column (nullable = false)
    private String title; // 제목

    private String content; // 내용 (null허용)

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDate; // 작성시간

    @UpdateTimestamp
    private LocalDateTime updateDate; // 수정시간

    // 아래는 디비저장용도 아니고, 조회용으로 사용하는 JPA 연관관계
    @OneToMany(mappedBy = "post")
    @Builder.Default // 초기화해도, 빌더인경우 이 아노테이션 써서 초기화시켜줘야함
    private List<HashTag> hashTags = new ArrayList<>();

    // 양방향 매핑에서 리스트쪽에 데이터를 추가하는 편의메서드 생성
    public void addHashTag(HashTag hashTag) {
        hashTags.add(hashTag);
        if (this != hashTag.getPost()) {
            hashTag.setPost(this);
        }
    }



}
