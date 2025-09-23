package project_5headers.com.team_project.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import project_5headers.com.team_project.security.model.PrincipalUser;
import project_5headers.com.team_project.service.ChatService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5173"})
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/estimate")
    public Map<String, String> estimate(@RequestBody Map<String, Object> payload,
                                        @AuthenticationPrincipal PrincipalUser principalUser) {
        String purpose = (String) payload.get("purpose");
        Integer cost = (Integer) payload.get("cost");
        String title = (String) payload.getOrDefault("title", "제목 없음");

        // 로그인된 유저 ID 사용
        Integer userId = principalUser.getUserId();

        String response = chatService.askGPTAndSave(userId, title, purpose, cost);
        return Map.of("data", response);
    }

}
