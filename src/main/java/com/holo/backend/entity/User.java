package com.holo.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * users 테이블과 매핑되는 Entity.
 * JPA가 이 클래스를 보고 테이블의 각 컬럼과 필드를 연결한다.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // BIGSERIAL (DB가 자동 번호 매김)
    private Long id;

    @Column(name = "kakao_id", nullable = false, unique = true)
    private String kakaoId;

    private String nickname;

    private String region;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "trust_temperature")
    private BigDecimal trustTemperature;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}
