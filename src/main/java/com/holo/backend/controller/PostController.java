package com.holo.backend.controller;

import com.holo.backend.dto.PostResponse;
import com.holo.backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * PostController - 게시물 관련 HTTP 요청을 처리하는 곳.
 * 프론트가 GET /api/posts 로 요청하면 여기서 응답한다.
 */
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor // final 필드를 자동으로 생성자 주입 (postRepository)
public class PostController {

    private final PostRepository postRepository;

    /** 게시물 목록 조회 */
    @GetMapping
    public List<PostResponse> getPosts() {
        return postRepository.findAllWithParticipants().stream()
                .map(PostResponse::from) // 각 Post → PostResponse 로 변환
                .toList();
    }
}
