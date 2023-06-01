package com.study.jpa.chap05_practice.Service;

import com.study.jpa.chap05_practice.dto.PageDTO;
import com.study.jpa.chap05_practice.dto.PageResponseDTO;
import com.study.jpa.chap05_practice.dto.PostDetailResponseDTO;
import com.study.jpa.chap05_practice.dto.PostListResponseDTO;
import com.study.jpa.chap05_practice.entity.Post;
import com.study.jpa.chap05_practice.repository.HashTagRepository;
import com.study.jpa.chap05_practice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional // JPA 레파지토리를 사용하는 빈은 반드시 트랜잭션 아노테이션 필수
public class PostService {

    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;

    public PostListResponseDTO getPosts(PageDTO dto) {

        //Pageable 객체 생성
        Pageable pageable = PageRequest.of(
                dto.getPage() - 1,
                dto.getSize(),
                Sort.by("createDate").descending() // 엔터티 필드명
        );

        // 디비에 가서 게시물 목록 조회
        Page<Post> posts = postRepository.findAll(pageable);

        // 게시물 정보 꺼내기
        List<Post> postList = posts.getContent();
        List<PostDetailResponseDTO> postDetailResponseDTOList = postList.stream().map(post-> new PostDetailResponseDTO(post)).collect(Collectors.toList());

        // 디비에서 조회한 정보를 JSON형태에 맞는 DTO로 변환



        return PostListResponseDTO.builder()
                .count(postDetailResponseDTOList.size()) // 총 게시물 수가 아니라 조회된 게시물 수
                .pageInfo(new PageResponseDTO<Post>(posts)) // 이 안에 전체 총 게시물 수
                .posts(postDetailResponseDTOList)
                .build();
    }
}
