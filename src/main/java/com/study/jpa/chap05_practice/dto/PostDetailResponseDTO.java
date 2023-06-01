package com.study.jpa.chap05_practice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.jpa.chap05_practice.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetailResponseDTO {

    private String author; // 게시물 작성자
    private String title; // 게시물 제목
    private String content; // 게시물 내용
    private List<String> hashTags; // 문자열배열로 해쉬태그

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime regDate;

    // 엔터티를 DTO로 변환하는 생성자
    public PostDetailResponseDTO(Post post) {
        this.author = post.getWriter();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.regDate = post.getCreateDate();
        this.hashTags = post.getHashTags().stream().map(ht->ht.getTagName()).collect(Collectors.toList());
    }

}
