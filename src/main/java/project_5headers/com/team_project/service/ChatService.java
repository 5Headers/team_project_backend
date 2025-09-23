package project_5headers.com.team_project.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import project_5headers.com.team_project.entity.Estimate;
import project_5headers.com.team_project.repository.EstimateRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.*;

@Service
public class ChatService {

    @Value("${openai.api-key}")
    private String apiKey;

    private final WebClient webClient;
    private final EstimateRepository estimateRepository;

    public ChatService(WebClient.Builder builder, EstimateRepository estimateRepository) {
        this.webClient = builder.baseUrl("https://api.openai.com/v1").build();
        this.estimateRepository = estimateRepository;
    }

    public String askGPTAndSave(Integer userId, String title, String purpose, int cost) {

        System.out.println("userId = " + userId);
        try {
            // GPT 프롬프트
            String prompt = String.format(
                    "사용자가 %s 용도로 %d원 예산의 PC를 원합니다. "
                            + "게임용 PC 추천 견적을 제공하고, 반드시 '총 가격은 ~원입니다' 형식으로 명시해주세요.",
                    purpose, cost
            );

            Map<String, Object> requestBody = Map.of(
                    "model", "gpt-4o-mini",
                    "messages", List.of(
                            Map.of("role", "system", "content", "You are a helpful PC builder assistant."),
                            Map.of("role", "user", "content", prompt)
                    )
            );

            // GPT 호출
            Mono<Map> response = webClient.post()
                    .uri("/chat/completions")
                    .header("Authorization", "Bearer " + apiKey)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class);

            Map res = response.block();
            String gptContent = "GPT 응답이 없습니다.";

            if (res != null && res.containsKey("choices")) {
                List choices = (List) res.get("choices");
                if (!choices.isEmpty()) {
                    Map firstChoice = (Map) choices.get(0);
                    Map message = (Map) firstChoice.get("message");
                    gptContent = (String) message.get("content");
                }
            }

            // GPT 응답에서 총 가격 파싱
            int totalPrice = cost; // 기본값
            if (gptContent != null && !gptContent.isEmpty()) {
                Pattern pattern = Pattern.compile("총\\s*가격.*?([0-9,]+)\\s*원");
                Matcher matcher = pattern.matcher(gptContent);
                if (matcher.find()) {
                    String numStr = matcher.group(1).replaceAll(",", "");
                    totalPrice = Integer.parseInt(numStr);
                }
            }

            // DB 저장
            Estimate estimate = Estimate.builder()
                    .userId(userId)
                    .title(title)
                    .purpose(purpose)
                    .budget(cost)
                    .totalPrice(totalPrice) // GPT에서 파싱한 총 가격 사용
                    .bookmarkCount(0)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            estimateRepository.addEstimate(estimate);

            return gptContent;

        } catch (Exception e) {
            e.printStackTrace();
            return "서버 오류: " + e.getMessage();
        }
    }
}
