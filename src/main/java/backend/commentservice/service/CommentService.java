package backend.commentservice.service;

import backend.commentservice.dto.CommentResponse;
import backend.commentservice.dto.ListCommentResponse;
import backend.commentservice.entity.CommentEntity;
import backend.commentservice.exception.CommentNotFoundException;
import backend.commentservice.exception.UnauthorizedException;
import backend.commentservice.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public ListCommentResponse getCommentsPage(Long newsId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentEntity> commentPage = commentRepository.findByNewsId(newsId, pageable);

        List<CommentResponse> commentResponses = commentPage.getContent().stream().map(this::toCommentResponse).toList();
        return new ListCommentResponse(commentResponses, commentResponses.size());
    }

    public CommentResponse getCommentById(Long newsId, Long commentId) {
        return toCommentResponse(findCommentByIdAndNewsId(commentId, newsId));
    }

    public CommentResponse createComment(Long newsId, String text) {
        String author = getCurrentUserName();
        CommentEntity comment = new CommentEntity();
        comment.setAuthor(author);
        comment.setText(text);
        comment.setNewsId(newsId);
        comment.setTime(Instant.now());
        return toCommentResponse(commentRepository.save(comment));
    }

    @Transactional
    public CommentResponse updateComment(Long newsId, Long commentId, String text) {
        CommentEntity comment = findCommentByIdAndNewsId(commentId, newsId);
        String author = getCurrentUserName();
        if (!comment.getAuthor().equals(author) && !hasAdminRole()) {
            throw new UnauthorizedException("Access denied");
        }

        comment.setText(text);
        return toCommentResponse(commentRepository.save(comment));
    }

    @Transactional
    public void deleteComment(Long newsId, Long commentId) {
        CommentEntity comment = findCommentByIdAndNewsId(commentId, newsId);
        String author = getCurrentUserName();
        if (!comment.getAuthor().equals(author) && !hasAdminRole()) {
            throw new UnauthorizedException("Access denied");
        }

        commentRepository.delete(comment);
    }

    @Transactional
    public void deleteCommentsByNewsId(Long newsId) {
        commentRepository.deleteAllByNewsId(newsId);
    }

    private CommentEntity findCommentByIdAndNewsId(Long commentId, Long newsId) {
        return commentRepository.findById(commentId)
                .filter(com -> com.getNewsId().equals(newsId))
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
    }

    private Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnauthorizedException("Access denied: no authentication found");
        } else {
            return authentication;
        }
    }

    private boolean hasAdminRole() {
        return getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    private String getCurrentUserName() {
        return getAuthentication().getName();
    }

    private CommentResponse toCommentResponse(CommentEntity commentEntity) {
        return new CommentResponse(
                commentEntity.getId(),
                commentEntity.getTime(),
                commentEntity.getText(),
                commentEntity.getAuthor(),
                commentEntity.getNewsId()
        );
    }
}
