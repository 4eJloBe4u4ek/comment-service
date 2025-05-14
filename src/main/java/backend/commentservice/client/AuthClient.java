package backend.commentservice.client;

import backend.commentservice.config.CommentServiceConfig;
import backend.commentservice.dto.UserInfoResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthClient {
    private final WebClient authClient;

    public AuthClient(CommentServiceConfig commentConfig) {
        this.authClient = WebClient.builder().baseUrl(commentConfig.authService().baseUrl()).build();
    }

    public UserInfoResponse getUserById(Long userId) {
        return authClient.get()
                .uri("/auth/users/{id}", userId)
                .retrieve()
                .bodyToMono(UserInfoResponse.class)
                .block();
    }
}
