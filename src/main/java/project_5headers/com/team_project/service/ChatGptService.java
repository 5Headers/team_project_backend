package project_5headers.com.team_project.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
@Service

public class ChatGptService {

        @Value("${openai.api-key}")
        private String apiKey;

        private final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

        public String getChatGPTResponse(String prompt) {
            WebClient webClient = WebClient.builder()
                    .baseUrl(OPENAI_URL)
                    .defaultHeader("Authorization", "Bearer " + apiKey)
                    .defaultHeader("Content-Type", "application/json")
                    .build();
            System.out.println("API KEY: " + apiKey);
            Map<String, Object> message = new HashMap<>();
            message.put("role", "user");
            message.put("content", prompt);

            Map<String, Object> body = new HashMap<>();
            body.put("model", "gpt-3.5-turbo"); // 무료 크레딧으로 사용 가능한 모델
            body.put("messages", new Map[]{message});
            body.put("temperature", 0.7);

            Mono<Map> response = webClient.post()
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Map.class);

            Map result = response.block(); // 동기 호출
            if (result != null && result.containsKey("choices")) {
                Object[] choices = ((java.util.List) result.get("choices")).toArray();
                if (choices.length > 0) {
                    Map choice = (Map) choices[0];
                    Map messageMap = (Map) choice.get("message");
                    return (String) messageMap.get("content");
                }
            }
            return "응답이 없습니다.";
        }
    }

