package project_5headers.com.team_project.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class NaverShoppingService {

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    private final WebClient webClient;

    public NaverShoppingService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://openapi.naver.com/v1/search").build();
    }

    public String searchFirstProductLink(String query) {
        try {
            Mono<String> response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/shop.json")
                            .queryParam("query", query)
                            .queryParam("display", 1) // 상위 1개만
                            .build())
                    .header("X-Naver-Client-Id", clientId)
                    .header("X-Naver-Client-Secret", clientSecret)
                    .retrieve()
                    .bodyToMono(String.class);

            String res = response.block();
            if (res != null && res.contains("link")) {
                // JSON에서 "link" 값만 간단히 추출 (정규식)
                String pattern = "\"link\":\"([^\"]+)\"";
                java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(pattern).matcher(res);
                if (matcher.find()) {
                    return matcher.group(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "#"; // 실패 시 링크 대체
    }
}
