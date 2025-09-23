package project_5headers.com.team_project.controller;

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
    public Map<String, String> estimate(@RequestBody Map<String, Object> payload) {
        String purpose = (String) payload.get("purpose");
        Integer cost = (Integer) payload.get("cost");
        String title = (String) payload.getOrDefault("title", "제목 없음");
        Integer userId = (Integer) payload.getOrDefault("userId", 1); // 로그인 구현 전 기본값

        String response = chatService.askGPTAndSave(userId, title, purpose, cost);
        return Map.of("data", response);
    }
}
