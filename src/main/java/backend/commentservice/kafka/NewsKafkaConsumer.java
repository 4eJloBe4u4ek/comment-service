package backend.commentservice.kafka;

import backend.commentservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsKafkaConsumer {
    private final CommentService commentService;

    @KafkaListener(
            topics = "${spring.kafka.topics.news-deleted}",
            groupId = "comment-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(ConsumerRecord<Long, NewsDeletedEvent> record, Acknowledgment acknowledgment) {
        commentService.deleteCommentsByNewsId(record.value().newsId());
        acknowledgment.acknowledge();
    }
}
