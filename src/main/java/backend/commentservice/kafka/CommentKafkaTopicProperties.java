package backend.commentservice.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.kafka.topics")
public class CommentKafkaTopicProperties {
    private String newsDeleted;
}
