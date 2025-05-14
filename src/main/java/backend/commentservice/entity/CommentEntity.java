package backend.commentservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time", nullable = false)
    private Instant time;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "news_id", nullable = false)
    private Long newsId;

    @Column(name = "author_id", nullable = false)
    private Long authorId;
}
