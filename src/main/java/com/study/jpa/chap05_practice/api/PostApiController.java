package com.study.jpa.chap05_practice.api;

import com.study.jpa.chap05_practice.Service.PostService;
import com.study.jpa.chap05_practice.dto.PageDTO;
import com.study.jpa.chap05_practice.dto.PostListResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostApiController {

    // 리소스 : 게시물 (Post)
/*
     게시물 목록 조회:  /posts       - GET
     게시물 개별 조회:  /posts/{id}  - GET
     게시물 등록:      /posts       - POST
     게시물 수정:      /posts/{id}  - PATCH
     게시물 삭제:      /posts/{id}  - DELETE
 */

    private final PostService postService;

    @GetMapping
    public ResponseEntity<?> list(PageDTO pageDTO) {
        log.info("/api/v1/posts?page={}&size={}", pageDTO.getPage(), pageDTO.getSize()); // 몇페이지를 몇개씩보여줘

        // 서비스.게시물목록가져와
        // 클라이언트개발자가 달라는 형태로 주기 (객체 담아줘 count:, pageInfo객체, posts배열 )
        PostListResponseDTO dto = postService.getPosts(pageDTO);

        return ResponseEntity.ok().body(dto);
    }





}
