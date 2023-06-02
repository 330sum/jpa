package com.study.jpa.chap05_practice.api;

import com.study.jpa.chap05_practice.Service.PostService;
import com.study.jpa.chap05_practice.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    /* 전체조회 */
    @GetMapping
    public ResponseEntity<?> list(PageDTO pageDTO) {
        log.info("/api/v1/posts?page={}&size={}", pageDTO.getPage(), pageDTO.getSize()); // 몇페이지를 몇개씩보여줘

        // 서비스.게시물목록가져와
        // 클라이언트개발자가 달라는 형태로 주기 (객체 담아줘 count:, pageInfo객체, posts배열 )
        PostListResponseDTO dto = postService.getPosts(pageDTO);

        return ResponseEntity.ok().body(dto);
    }

    /* 개별조회 */
    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        log.info("/api/v1/posts/{} GET", id);

        try {
            PostDetailResponseDTO dto = postService.getDetail(id);
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    /* 게시물등록 */
    @PostMapping
    public ResponseEntity<?> create(
            @Validated @RequestBody PostCreateDTO dto
            , BindingResult result // 검증 에러정보를 가진 객체
    ) {
        log.info("/api/v1/post POST!! - payload: {}", dto);

        if (dto == null) {
            return ResponseEntity.badRequest().body("등록 게시물 정보를 전달해주세요");
        }

        ResponseEntity<List<FieldError>> fieldErrors = getValidatedResult(result);
        if (fieldErrors != null) return fieldErrors;

        // 서비스의 insert RuntimeException 받음
        try {
            PostDetailResponseDTO postDetailResponseDTO = postService.insert(dto);
            return ResponseEntity.ok().body(postDetailResponseDTO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("미안 서버 터짐 원인:" + e.getMessage());
        }
    }

    private static ResponseEntity<List<FieldError>> getValidatedResult(BindingResult result) {
        if(result.hasErrors()) { // 입력값 검증에 걸림
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(err -> {
                log.warn("invalid client data - {}", err.toString());
            });

            return ResponseEntity.badRequest().body(fieldErrors);
        }
        return null;
    }

    /* 수정 */
    // 스프링에서는 DTO를 쓰기때문에 객체가 갈아끼워져서 PUT과 PATCH의 차이가 없음
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> update(@Validated @RequestBody PostModifyDTO dto
                                    , BindingResult result
                                    , HttpServletRequest request) {

        log.info("/api/v1/posts {}!! - dto: {}", request.getMethod(), dto);

        ResponseEntity<List<FieldError>> fieldErrors = getValidatedResult(result);
        if (fieldErrors != null) return fieldErrors;

        try {
            PostDetailResponseDTO responseDTO = postService.modify(dto);
            return ResponseEntity.ok(responseDTO);
            // 헤더없으면 ok 안에 넣어도 됨
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    /* 삭제 */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.info("\"/api/v1/posts/{}  DELETE!! ", id);

        try {
            postService.delete(id);
            return ResponseEntity.ok("DEL SUCCESS!!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }





}
