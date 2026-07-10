package com.holo.backend.controller;

import com.holo.backend.dto.PostCreateRequest;
import com.holo.backend.dto.PostResponse;
import com.holo.backend.entity.Participation;
import com.holo.backend.entity.Post;
import com.holo.backend.entity.User;
import com.holo.backend.repository.ParticipationRepository;
import com.holo.backend.repository.PostRepository;
import com.holo.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * PostController - 게시물 관련 HTTP 요청을 처리하는 곳.
 * 프론트가 GET /api/posts 로 요청하면 목록을, POST /api/posts 로 보내면 새 글을 저장한다.
 */
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor // final 필드를 자동으로 생성자 주입
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ParticipationRepository participationRepository;

    // 로그인 기능이 아직 없어서, 새 글의 작성자를 홍길동(id=1)으로 고정한다.
    // 나중에 로그인이 붙으면 이 값을 "현재 로그인한 사용자"로 교체하면 된다.
    private static final Long TEMP_AUTHOR_ID = 1L;

    /** 게시물 목록 조회 */
    @GetMapping
    public List<PostResponse> getPosts() {
        return postRepository.findAllWithParticipants().stream()
                .map(PostResponse::from) // 각 Post → PostResponse 로 변환
                .toList();
    }

    /** 게시물 하나 조회 (상세 페이지) */
    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        Post post = postRepository.findByIdWithParticipants(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다. id=" + id));
        return PostResponse.from(post);
    }

    /** 게시물 등록 (글쓰기) */
    @PostMapping
    @Transactional // 게시물 저장 + 참여자 저장을 하나의 작업으로 묶음
    public PostResponse createPost(@RequestBody PostCreateRequest req) {
        // 작성자(임시 유저)를 불러온다.
        User author = userRepository.findById(TEMP_AUTHOR_ID)
                .orElseThrow(() -> new IllegalStateException(
                        "작성자(임시 유저 id=" + TEMP_AUTHOR_ID + ")를 찾을 수 없습니다."));

        // 프론트가 보낸 값으로 새 게시물을 만든다.
        Post post = new Post();
        post.setAuthor(author);
        post.setTitle(req.title());
        post.setCategory(req.category());
        post.setStatus("모집중"); // 새 글은 항상 "모집중" 으로 시작
        post.setPrice(emptyToNull(req.price()));
        post.setCapacity(req.capacity());
        post.setLocation(emptyToNull(req.location()));
        post.setContent(emptyToNull(req.content()));
        post.setCreatedAt(OffsetDateTime.now());

        Post saved = postRepository.save(post);

        // 정원이 있으면 작성자를 첫 참여자로 등록 → 목록에서 "1/N" 로 시작한다.
        if (req.capacity() != null) {
            Participation participation = new Participation();
            participation.setPost(saved);
            participation.setUser(author);
            participation.setJoinedAt(OffsetDateTime.now());
            participationRepository.save(participation);
            saved.getParticipants().add(participation); // 응답의 참여 인원 계산에 반영
        }

        return PostResponse.from(saved);
    }

    /** 빈 문자열("")이나 공백만 있으면 null 로 바꿔준다 (선택 입력값 정리용) */
    private static String emptyToNull(String value) {
        return (value == null || value.isBlank()) ? null : value;
    }
}
