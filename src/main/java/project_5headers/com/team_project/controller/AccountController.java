package project_5headers.com.team_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_5headers.com.team_project.dto.ApiRespDto;
import project_5headers.com.team_project.dto.account.ChangePasswordReqDto;
import project_5headers.com.team_project.security.model.PrincipalUser;
import project_5headers.com.team_project.service.AccountService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;



    // 비밀번호 변경
    @PostMapping("/change/password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordReqDto changePasswordReqDto,
            @AuthenticationPrincipal PrincipalUser principalUser) {
        ApiRespDto<?> response = accountService.changePassword(changePasswordReqDto, principalUser);
        return ResponseEntity.ok(response);
    }

}