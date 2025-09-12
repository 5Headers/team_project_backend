package project_5headers.com.team_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_5headers.com.team_project.entity.EstimatePart;
import project_5headers.com.team_project.service.EstimatePartService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estimate-part")
public class EstimatePartController {

    @Autowired
    private EstimatePartService estimatePartService;

    // 부품 추가
    @PostMapping("/add")
    public ResponseEntity<?> addEstimatePart(@RequestBody EstimatePart estimatePart) {
        return ResponseEntity.ok(estimatePartService.addEstimatePart(estimatePart));
    }

    // ID로 부품 조회
    @GetMapping("/{estimatePartId}")
    public ResponseEntity<?> getEstimatePartById(@PathVariable Integer estimatePartId) {
        Optional<EstimatePart> part = estimatePartService.getEstimatePartById(estimatePartId);
        return part.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 견적 ID로 부품 목록 조회
    @GetMapping("/list/{estimateId}")
    public ResponseEntity<?> getPartsByEstimateId(@PathVariable Integer estimateId) {
        List<EstimatePart> parts = estimatePartService.getPartsByEstimateId(estimateId);
        return ResponseEntity.ok(parts);
    }

    // 부품 수정
    @PostMapping("/update")
    public ResponseEntity<?> updateEstimatePart(@RequestBody EstimatePart estimatePart) {
        return ResponseEntity.ok(estimatePartService.updateEstimatePart(estimatePart));
    }

    // 부품 삭제
    @PostMapping("/remove/{estimatePartId}")
    public ResponseEntity<?> removeEstimatePart(@PathVariable Integer estimatePartId) {
        boolean deleted = estimatePartService.removeEstimatePartById(estimatePartId);
        if (deleted) {
            return ResponseEntity.ok("삭제 성공");
        } else {
            return ResponseEntity.badRequest().body("삭제 실패");
        }
    }
}
