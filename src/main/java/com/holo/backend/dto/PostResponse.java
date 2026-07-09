package com.holo.backend.dto;

import com.holo.backend.entity.Post;

import java.time.Duration;
import java.time.OffsetDateTime;

/**
 * PostResponse - 프론트에 내려줄 게시물 응답 형태 (DTO).
 * Entity를 그대로 내보내지 않고, 프론트가 쓰는 모양으로 변환해서 보낸다.
 * (record: 값만 담는 간단한 자바 문법. 자동으로 getter 등을 만들어줌)
 */
public record PostResponse(
        String id,
        String title,
        String category,
        String status,
        String price,
        String participants, // "2/4" 형태 (정원이 있을 때만)
        String location,
        String content,
        String timestamp
) {
    /** Post Entity → PostResponse 로 변환 */
    public static PostResponse from(Post p) {
        String participants = (p.getCapacity() != null)
                ? p.getParticipants().size() + "/" + p.getCapacity()
                : null;

        return new PostResponse(
                String.valueOf(p.getId()),
                p.getTitle(),
                p.getCategory(),
                p.getStatus(),
                p.getPrice(),
                participants,
                p.getLocation(),
                p.getContent(),
                timeAgo(p.getCreatedAt())
        );
    }

    /** 작성 시각을 "방금 전 / N분 전 / N시간 전 / N일 전" 으로 변환 */
    private static String timeAgo(OffsetDateTime created) {
        if (created == null) return "";
        long minutes = Duration.between(created, OffsetDateTime.now()).toMinutes();
        if (minutes < 1) return "방금 전";
        if (minutes < 60) return minutes + "분 전";
        long hours = minutes / 60;
        if (hours < 24) return hours + "시간 전";
        return (hours / 24) + "일 전";
    }
}
