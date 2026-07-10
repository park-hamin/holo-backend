package com.holo.backend.repository;

import com.holo.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository - 사용자 DB 접근 담당.
 * 지금은 작성자(임시 유저)를 id로 조회하는 데 사용한다.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
