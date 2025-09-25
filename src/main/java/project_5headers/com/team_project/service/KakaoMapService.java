package project_5headers.com.team_project.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;


@Service
public class KakaoMapService {

    @Value("${kakao.api.key}")
    private String apiKey; // ✅ 반드시 REST API 키 사용

    private final RestTemplate restTemplate = new RestTemplate();

    // ✅ 사용자 위치 기반 카테고리 검색 (예: 카페 CE7)
    public String searchNearby(String category, double x, double y, int radius) {
        try {
            String url = String.format(
                    "https://dapi.kakao.com/v2/local/search/category.json?category_group_code=%s&x=%f&y=%f&radius=%d",
                    category, x, y, radius
            );

            // ===== 디버깅 로그 =====
            System.out.println("📡 [KakaoMapService] 카카오 API 호출 시작");
            System.out.println("URL = " + url);
            System.out.println("Authorization 헤더 = KakaoAK " + apiKey);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + apiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            System.out.println("✅ [KakaoMapService] 응답 코드 = " + response.getStatusCode());
            return response.getBody();

        } catch (Exception e) {
            System.err.println("❌ [KakaoMapService] 카카오 API 호출 중 오류 발생");
            e.printStackTrace();
            return "{\"error\":\"카카오 API 호출 실패\"}";
        }
    }
    public String searchComputerStores(double x, double y, int radius) {
        try {
            String keyword = URLEncoder.encode("컴퓨터 매장", StandardCharsets.UTF_8);
            String url = String.format(
                    "https://dapi.kakao.com/v2/local/search/keyword.json?query=%s&x=%f&y=%f&radius=%d&sort=distance",
                    keyword, x, y, radius
            );

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + apiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, String.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"컴퓨터 매장 검색 실패\"}";
        }
    }

}
