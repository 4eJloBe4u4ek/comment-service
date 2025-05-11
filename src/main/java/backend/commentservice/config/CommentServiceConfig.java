package backend.commentservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.news-service", ignoreInvalidFields = false)
public record CommentServiceConfig(String baseUrl) {
}
