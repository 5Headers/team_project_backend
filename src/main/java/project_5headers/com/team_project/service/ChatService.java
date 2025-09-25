package project_5headers.com.team_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import project_5headers.com.team_project.entity.Estimate;
import project_5headers.com.team_project.entity.EstimatePart;
import project_5headers.com.team_project.repository.EstimateRepository;
import project_5headers.com.team_project.repository.EstimatePartRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.*;

@Service
public class ChatService {

    @Autowired
    private NaverShoppingService naverShoppingService;

    @Value("${openai.api-key}")
    private String apiKey;

    private final WebClient webClient;
    private final EstimateRepository estimateRepository;
    private final EstimatePartRepository estimatePartRepository;

    public ChatService(WebClient.Builder builder,
                       EstimateRepository estimateRepository,
                       EstimatePartRepository estimatePartRepository) {
        this.webClient = builder.baseUrl("https://api.openai.com/v1").build();
        this.estimateRepository = estimateRepository;
        this.estimatePartRepository = estimatePartRepository;
    }

    public String askGPTAndSave(Integer userId, String title, String purpose, int cost) {
        try {
            // ===== 1. GPT 프롬프트 =====
            String prompt = String.format(
                    "사용자가 %s 용도로 %d원 예산의 PC를 원합니다. "
                            + "게임용 PC 추천 견적을 제공하고, 반드시 '총 가격은 ~원입니다' 형식으로 명시해주세요. "
                            + "그리고 부품 리스트는 '**카테고리**: 제품명 - 가격원 ([링크](https://...))' 형식으로 작성해주세요.",
                    purpose, cost
            );

            Map<String, Object> requestBody = Map.of(
                    "model", "gpt-4o-mini",
                    "messages", List.of(
                            Map.of("role", "system", "content", "You are a helpful PC builder assistant."),
                            Map.of("role", "user", "content", prompt)
                    )
            );

            // ===== 2. GPT 호출 =====
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

            // ===== 3. 총 가격 파싱 =====
            int totalPrice = cost; // 기본값
            if (gptContent != null && !gptContent.isEmpty()) {
                Pattern pattern = Pattern.compile("총\\s*가격.*?([0-9,]+)\\s*원");
                Matcher matcher = pattern.matcher(gptContent);
                if (matcher.find()) {
                    String numStr = matcher.group(1).replaceAll(",", "");
                    totalPrice = Integer.parseInt(numStr);
                }
            }

            // ===== 4. Estimate 저장 =====
            Estimate estimate = Estimate.builder()
                    .userId(userId)
                    .title(title)
                    .purpose(purpose)
                    .budget(cost)
                    .totalPrice(totalPrice)
                    .bookmarkCount(0)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            estimateRepository.addEstimate(estimate);
            Integer estimateId = estimate.getEstimateId(); // ✅ PK 자동 세팅 (useGeneratedKeys 필요)

            // ===== 5. 부품 리스트 파싱 =====
            // 예시: **CPU**: AMD Ryzen 7 5800X3D - 600,000원 ([링크](https://example.com))
            // ===== 5. 부품 리스트 파싱 =====
            Pattern partPattern = Pattern.compile(
                    "\\*\\*(.*?)\\*\\*: (.*?) - ([0-9,]+)원"
            );
            Matcher matcher = partPattern.matcher(gptContent);

            while (matcher.find()) {
                String category = matcher.group(1).trim();
                String name = matcher.group(2).trim();
                int price = Integer.parseInt(matcher.group(3).replaceAll(",", ""));

                // ✅ 네이버 링크 검색
                String link = naverShoppingService.searchFirstProductLink(name);

                EstimatePart part = EstimatePart.builder()
                        .estimateId(estimateId)
                        .category(category)
                        .name(name)
                        .price(price)
                        .link(link)
                        .storeType("ONLINE")
                        .createdAt(LocalDateTime.now())
                        .build();

                estimatePartRepository.addEstimatePart(part);
            }


            return gptContent;

        } catch (Exception e) {
            e.printStackTrace();
            return "서버 오류: " + e.getMessage();
        }
    }
}
