package backend.commentservice.dto;

import java.time.Instant;

public record CommentResponse(Long id, Instant time, String text, String author, Long newsId) {
}
