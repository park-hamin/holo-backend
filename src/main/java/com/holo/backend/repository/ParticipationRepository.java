package com.holo.backend.repository;

import com.holo.backend.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ParticipationRepository - 참여 기록(post_participants) DB 접근 담당.
 * 글을 새로 등록할 때 작성자를 첫 참여자로 저장하는 데 사용한다.
 */
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
}
