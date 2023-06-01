package com.study.jpa.chap05_practice.dto;

import lombok.*;

import java.util.List;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostListResponseDTO {

    private int count; //  총 게시물 수가 아니라 조회된 게시물 수
    private PageResponseDTO pageInfo; // 페이지 렌더링 정보 (객체형태로) (이 안에 전체 총 게시물수)
    private List<PostDetailResponseDTO> posts; // 게시물 렌더링 정보 (배열형태로, 그 안에 게시물 객체)
    // 엔터티는 DTO에 절대 쓰지말것. 1:1로 똑같더라도
}
