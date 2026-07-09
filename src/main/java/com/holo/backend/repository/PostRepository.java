package com.holo.backend.repository;

import com.holo.backend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * PostRepository - 게시물 DB 접근 담당.
 * JpaRepository를 상속하면 기본 CRUD(저장/조회/삭제)가 자동으로 생김.
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * 게시물 목록을 참여 기록까지 한 번에 불러옴 (최신순).
     * LEFT JOIN FETCH: 참여 기록을 같이 로딩해서 나중에 participants.size()를 안전하게 셀 수 있게 함.
     * DISTINCT: JOIN 때문에 생기는 중복 게시물 제거.
     */
    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.participants ORDER BY p.createdAt DESC")
    List<Post> findAllWithParticipants();
}
