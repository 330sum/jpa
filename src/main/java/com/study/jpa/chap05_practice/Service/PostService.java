package com.study.jpa.chap05_practice.Service;

import com.study.jpa.chap05_practice.dto.*;
import com.study.jpa.chap05_practice.entity.HashTag;
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

import java.sql.SQLException;
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

    public PostDetailResponseDTO getDetail(Long id) {

        Post postEntity = getPost(id);

        return new PostDetailResponseDTO(postEntity);
    }

    private Post getPost(Long id) {
        Post postEntity = postRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(id + "번 게시물 존재하지 않음")
                );
        return postEntity;
    }

    public PostDetailResponseDTO insert(final PostCreateDTO dto) throws RuntimeException {

        // 게시물 저장
        Post saved = postRepository.save(dto.toEntity());

        // 해시태그 저장
        List<String> hashTags = dto.getHashTags();
        if (hashTags != null && hashTags.size() > 0) {
            hashTags.forEach(ht -> {
                HashTag savedTag = hashTagRepository.save(
                        HashTag.builder()
                                .tagName(ht)
                                .post(saved)
                                .build()
                );
                saved.addHashTag(savedTag);
            });
        }

        return new PostDetailResponseDTO(saved);
    }


    public PostDetailResponseDTO modify(final PostModifyDTO dto) {

        // 수정 : 조회 -> 세터 -> save
        // 수정 전 데이터를 조회
        final Post postEntity = getPost(dto.getPostNo());
        // 세터
        postEntity.setTitle(dto.getTitle());
        postEntity.setContent(dto.getContent());
        // save
        Post modifiedPost = postRepository.save(postEntity);

        return new PostDetailResponseDTO(modifiedPost);
    }

    public void delete(Long id) throws RuntimeException, SQLException {

        postRepository.deleteById(id);
    }
}
