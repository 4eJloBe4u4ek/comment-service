package backend.commentservice.dto;

import java.time.Instant;

public record CommentResponse(Long id, Instant time, String text, Long authorId, String authorName, Long newsId, Long likesCount,
                              boolean likedByMe) {
}
