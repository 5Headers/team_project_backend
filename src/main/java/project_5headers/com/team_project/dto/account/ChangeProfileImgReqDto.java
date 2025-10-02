package project_5headers.com.team_project.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import project_5headers.com.team_project.entity.User;

@Data
@AllArgsConstructor
public class ChangeProfileImgReqDto {
    private Integer userId;
    private String profileImg;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .profileImg(profileImg)
                .build();
    }
}