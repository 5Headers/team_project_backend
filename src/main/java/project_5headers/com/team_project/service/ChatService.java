package project_5headers.com.team_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
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
                    "사용자가 %s 용도로 %d원 예산의 PC를 원합니다. " +
                            "PC 추천 견적을 제공하고, 반드시 '총 가격은 ~원입니다' 형식으로 명시해주세요. " +
                            "그리고 부품 리스트는 '**카테고리**: 제품명 - 가격원' 형식으로 작성해주세요.",
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
            Map res = webClient.post()
                    .uri("/chat/completions")
                    .header("Authorization", "Bearer " + apiKey)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

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
            int totalPrice = cost;
            if (gptContent != null && !gptContent.isEmpty()) {
                Pattern totalPattern = Pattern.compile("총\\s*가격.*?([0-9,]+)\\s*원");
                Matcher totalMatcher = totalPattern.matcher(gptContent);
                if (totalMatcher.find()) {
                    String numStr = totalMatcher.group(1).replaceAll(",", "");
                    totalPrice = Integer.parseInt(numStr);
                }
            }

            // ===== 4. Estimate 저장 =====
            Estimate estimate = Estimate.builder()
                    .userId(userId)
                    .title(title)
                    .purpose(purpose) // ⚡ 여기에는 "게임용" / "사무용" 같은 용도만 그대로 저장
                    .budget(cost)    // 예산은 budget 컬럼에 따로 저장
                    .totalPrice(totalPrice)
                    .bookmarkCount(0)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();


            estimateRepository.addEstimate(estimate);
            Integer estimateId = estimate.getEstimateId();

            // ===== 5. 부품 리스트 파싱 & 네이버 링크 치환 =====
            Pattern partPattern = Pattern.compile(
                    "\\*\\*(.*?)\\*\\*: (.*?) - ([0-9,만]+)원"
            );
            Matcher matcher = partPattern.matcher(gptContent);

            StringBuffer finalContentBuffer = new StringBuffer();

            while (matcher.find()) {
                String category = matcher.group(1).trim();
                String name = matcher.group(2).trim();

                // 가격 문자열 정리
                String priceStr = matcher.group(3).replaceAll(",", "");
                if (priceStr.contains("만")) {
                    priceStr = priceStr.replace("만", "0000");
                }
                int price = Integer.parseInt(priceStr);

                // ✅ 네이버 쇼핑 링크 검색
                String link = naverShoppingService.searchFirstProductLink(name);

                // DB 저장
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

                // GPT 응답 문자열도 네이버 링크로 교체
                String replacement = String.format("**%s**: %s - %,d원 ([링크](%s))",
                        category, name, price, link);
                matcher.appendReplacement(finalContentBuffer, Matcher.quoteReplacement(replacement));
            }
            matcher.appendTail(finalContentBuffer);

            String finalContent = finalContentBuffer.toString();

            return finalContent;

        } catch (Exception e) {
            e.printStackTrace();
            return "서버 오류: " + e.getMessage();
        }
    }
}
