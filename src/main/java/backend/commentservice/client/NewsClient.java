package backend.commentservice.client;

import backend.commentservice.config.CommentServiceConfig;
import backend.commentservice.exception.NewsNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class NewsClient {
    private final WebClient newsWebClient;

    public NewsClient(CommentServiceConfig config) {
        this.newsWebClient = WebClient.builder().baseUrl(config.newsService().baseUrl()).build();
    }

    public void verifyNewsExists(Long newsId) {
        newsWebClient
                .get()
                .uri("/news/" + newsId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new NewsNotFoundException("News not found")))
                .bodyToMono(Void.class)
                .block();
    }
}
