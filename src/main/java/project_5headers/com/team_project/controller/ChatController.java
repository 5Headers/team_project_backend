package project_5headers.com.team_project.controller;

import project_5headers.com.team_project.service.ChatService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:5173"})
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // POST body로 받도록 변경
    @PostMapping("/estimate")
    public Map<String, String> estimate(@RequestBody Map<String, Object> payload) {
        String purpose = (String) payload.get("purpose");
        Integer cost = (Integer) payload.get("cost");

        String response = chatService.askGPT(purpose, cost);
        return Map.of("data", response);
    }
}
