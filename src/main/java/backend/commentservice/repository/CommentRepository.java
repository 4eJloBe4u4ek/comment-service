package backend.commentservice.repository;

import backend.commentservice.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Page<CommentEntity> findByNewsId(Long newsId, Pageable pageable);

    void deleteAllByNewsId(Long newsId);
}
