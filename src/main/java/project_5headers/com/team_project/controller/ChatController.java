package project_5headers.com.team_project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project_5headers.com.team_project.dto.ApiRespDto;
import project_5headers.com.team_project.dto.EstimateRespDto;
import project_5headers.com.team_project.security.model.PrincipalUser;
import project_5headers.com.team_project.service.ChatService;

import java.util.Map;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5173"})
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // GPT 견적 API
    @PostMapping("/estimate")
    public ResponseEntity<?> estimate(@RequestBody Map<String, Object> payload,
                                      @AuthenticationPrincipal PrincipalUser principalUser) {
        String purpose = (String) payload.get("purpose");
        Integer cost = (Integer) payload.get("cost");
        String title = (String) payload.getOrDefault("title", "제목 없음");

        EstimateRespDto response = chatService.askGPTAndSave(
                principalUser.getUserId(),
                title,
                purpose,
                cost
        );

        return ResponseEntity.ok(new ApiRespDto<>("success", "견적 생성 완료", response));
    }
}
