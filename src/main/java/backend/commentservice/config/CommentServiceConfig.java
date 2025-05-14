package backend.commentservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app")
public record CommentServiceConfig(ServiceUrl newsService, ServiceUrl authService) {
    public record ServiceUrl(String baseUrl) {}
}
