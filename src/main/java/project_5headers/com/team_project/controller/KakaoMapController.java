package project_5headers.com.team_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_5headers.com.team_project.service.KakaoMapService;

@RestController
@RequestMapping("/api/maps")
@RequiredArgsConstructor
public class KakaoMapController {

    private final KakaoMapService kakaoMapService;

    // ✅ 카테고리 기반 주변 검색 (예: 카페, 음식점 등)
    @GetMapping("/nearby")
    public ResponseEntity<?> searchNearby(
            @RequestParam String category,
            @RequestParam double x,   // 경도 (longitude)
            @RequestParam double y,   // 위도 (latitude)
            @RequestParam(defaultValue = "1000") int radius // 반경 (m)
    ) {
        String result = kakaoMapService.searchNearby(category, x, y, radius);
        return ResponseEntity.ok(result);
    }

    // ✅ "컴퓨터 매장" 키워드 검색
    @GetMapping("/computer-stores")
    public ResponseEntity<?> searchComputerStores(
            @RequestParam double x,
            @RequestParam double y,
            @RequestParam(defaultValue = "2000") int radius
    ) {
        String result = kakaoMapService.searchComputerStores(x, y, radius);
        return ResponseEntity.ok(result);
    }
}
