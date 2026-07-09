package com.holo.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * posts 테이블과 매핑되는 Entity.
 */
@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 작성자: posts.author_id → users.id (여러 게시물이 한 명의 작성자를 가리킴)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    private String title;

    private String category;

    private String status;

    private String price;

    private Integer capacity;

    private String location;

    private Double lat;

    private Double lng;

    private String content;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    // 이 게시물의 참여 기록들 (post_participants). 참여 인원 = 이 리스트의 크기
    @OneToMany(mappedBy = "post")
    private List<Participation> participants = new ArrayList<>();
}
