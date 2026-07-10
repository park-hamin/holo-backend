package com.holo.backend.dto;

/**
 * PostCreateRequest - 글쓰기(게시물 등록) 요청 본문(JSON)을 담는 DTO.
 * 프론트가 POST /api/posts 로 보내는 값들을 그대로 받는다.
 * (선택 항목은 비어있으면 빈 문자열 "" 또는 null 로 올 수 있음 → 서버에서 정리)
 */
public record PostCreateRequest(
        String title,
        String category,
        String location,
        String content,
        String price,
        Integer capacity // 모집 인원 (없을 수도 있음)
) {}
