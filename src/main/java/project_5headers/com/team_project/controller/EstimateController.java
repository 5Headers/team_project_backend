package project_5headers.com.team_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project_5headers.com.team_project.entity.Estimate;
import project_5headers.com.team_project.security.model.PrincipalUser;
import project_5headers.com.team_project.service.EstimateService;
import project_5headers.com.team_project.service.ChatService;
import project_5headers.com.team_project.dto.ApiRespDto;
import project_5headers.com.team_project.dto.GptEstimateRequest;

@RestController
@RequestMapping("/estimate")
public class EstimateController {

    @Autowired
    private EstimateService estimateService;

    @Autowired
    private ChatService chatService;

    // ------------------ CRUD ------------------
    @PostMapping("/add")
    public ResponseEntity<?> addEstimate(@RequestBody Estimate estimate,
                                         @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(estimateService.addEstimate(estimate, principalUser));
    }

    @GetMapping("/{estimateId}")
    public ResponseEntity<?> getEstimateById(@PathVariable Integer estimateId) {
        return ResponseEntity.ok(estimateService.getEstimateById(estimateId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getEstimatesByUser(@PathVariable Integer userId,
                                                @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(estimateService.getEstimatesByUserId(userId, principalUser));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getEstimateList() {
        return ResponseEntity.ok(estimateService.getEstimateList());
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateEstimate(@RequestBody Estimate estimate,
                                            @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(estimateService.updateEstimate(estimate, principalUser));
    }

    @PostMapping("/remove/{estimateId}")
    public ResponseEntity<?> removeEstimate(@PathVariable Integer estimateId,
                                            @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(estimateService.removeEstimateById(estimateId, principalUser));
    }

    // ------------------ GPT 견적 ------------------
    @PostMapping("/gpt")
    public ResponseEntity<?> getEstimateFromGpt(@RequestBody GptEstimateRequest request,
                                                @AuthenticationPrincipal PrincipalUser principalUser) {
        String result = chatService.askGPTAndSave(
                principalUser.getUserId(),
                request.getTitle(),
                request.getPurpose(),
                request.getCost()
        );
        return ResponseEntity.ok(new ApiRespDto<>("success", "견적 생성 완료", result));
    }
}
