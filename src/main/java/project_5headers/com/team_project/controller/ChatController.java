package project_5headers.com.team_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import project_5headers.com.team_project.dto.ApiRespDto;
import project_5headers.com.team_project.service.ChatGptService;

@RestController
@RequestMapping("/chat")
@Service

public class ChatController {

    @Autowired
    private ChatGptService chatGPTService;

    @PostMapping("/estimate")
    public ResponseEntity<?> getEstimate(@RequestParam String cost, @RequestParam String purpose) {
        String prompt = "사용자의 컴퓨터 용도: " + purpose + ", 예산: " + cost + "원에 맞는 추천 견적을 알려줘.";
        String result = chatGPTService.getChatGPTResponse(prompt);
        return ResponseEntity.ok(new ApiRespDto<>("success", "견적 생성 완료", result));
    }
}
