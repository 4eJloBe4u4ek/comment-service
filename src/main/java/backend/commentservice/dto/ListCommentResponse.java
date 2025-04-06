package backend.commentservice.dto;

import java.util.List;

public record ListCommentResponse (List<CommentResponse> comments, int size) {
}
