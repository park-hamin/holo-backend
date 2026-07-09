package com.holo.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

/**
 * post_participants 테이블과 매핑되는 Entity.
 * "누가(user) 어느 게시물(post)에 참여했는지" 한 건을 나타냄.
 */
@Entity
@Table(name = "post_participants")
@Getter
@Setter
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "joined_at")
    private OffsetDateTime joinedAt;
}
