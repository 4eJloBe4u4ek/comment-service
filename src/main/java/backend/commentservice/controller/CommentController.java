package backend.commentservice.controller;

import backend.commentservice.dto.*;
import backend.commentservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/news/{newsId}/comments")
    public ResponseEntity<ListCommentResponse> getComments(@PathVariable Long newsId, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok().body(commentService.getCommentsPage(newsId, page, size));
    }

    @GetMapping("/news/{newsId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable Long newsId, @PathVariable Long commentId) {
        return ResponseEntity.ok().body(commentService.getCommentById(newsId, commentId));
    }

    @PostMapping("/news/{newsId}/comments")
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long newsId, @RequestBody CommentCreate commentCreate) {
        return ResponseEntity.ok().body(commentService.createComment(newsId, commentCreate.text()));
    }

    @PutMapping("/news/{newsId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long newsId, @PathVariable Long commentId, @RequestBody CommentUpdate commentUpdate) {
        return ResponseEntity.ok().body(commentService.updateComment(newsId, commentId, commentUpdate.text()));
    }

    @DeleteMapping("/news/{newsId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long newsId, @PathVariable Long commentId) {
        commentService.deleteComment(newsId, commentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/news/{newsId}/comments/{commentId}/like")
    public ResponseEntity<Void> like(@PathVariable Long commentId) {
        commentService.like(commentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/news/{newsId}/comments/{commentId}/like")
    public ResponseEntity<Void> unlike(@PathVariable Long commentId) {
        commentService.unlike(commentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/news/{newsId}/comments/{commentId}/like")
    public ResponseEntity<LikeStatusResponse> getLikes(@PathVariable Long commentId) {
        long count = commentService.countLikes(commentId);
        boolean liked = commentService.hasLiked(commentId);
        return ResponseEntity.ok(new LikeStatusResponse(count, liked));
    }
}
