package project_5headers.com.team_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_5headers.com.team_project.dto.ApiRespDto;
import project_5headers.com.team_project.dto.account.ChangePasswordReqDto;
import project_5headers.com.team_project.dto.account.ChangeProfileImgReqDto;
import project_5headers.com.team_project.dto.account.ResetPasswordReqDto;
import project_5headers.com.team_project.entity.User;
import project_5headers.com.team_project.repository.UserRepository;
import project_5headers.com.team_project.security.model.PrincipalUser;
import project_5headers.com.team_project.service.AccountService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserRepository userRepository;



    // 비밀번호 변경
    @PostMapping("/change/password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordReqDto changePasswordReqDto,
            @AuthenticationPrincipal PrincipalUser principalUser) {
        ApiRespDto<?> response = accountService.changePassword(changePasswordReqDto, principalUser);
        return ResponseEntity.ok(response);
    }
    //비밀번호 찾기
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordReqDto resetPasswordReqDto) {
        ApiRespDto<?> response = accountService.resetPassword(resetPasswordReqDto);
        return ResponseEntity.ok(response);
    }

    // 아이디 찾기 (이름 + 이메일)
    @PostMapping("/find-id")
    public ResponseEntity<?> findId(
            @RequestParam String name,
            @RequestParam String email) {
        ApiRespDto<?> response = accountService.findUserIdByNameAndEmail(name, email);
        return ResponseEntity.ok(response);
    }


    // 프로필 이미지 변경하기
    public ApiRespDto<?> changeProfileImg(ChangeProfileImgReqDto changeProfileImgReqDto, PrincipalUser principalUser) {
        Optional<User> userByUserId = userRepository.getUserByUserId(principalUser.getUserId());
        if (userByUserId.isEmpty()) {
            return new ApiRespDto<>("failed", "존재하지 않는 사용자입니다.", null);

        }

        if (!Objects.equals(changeProfileImgReqDto.getUserId(), principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 요청입니다.", null);
        }

        int result = userRepository.changeProfileImg(changeProfileImgReqDto.toEntity());

        if(result != 1) {
            return new ApiRespDto<>("failed", "문제가 발생했습니다.", null);
        }

        return new ApiRespDto<>("success", "프로필 이미지가 변경되었습니다.", null);
    }


}