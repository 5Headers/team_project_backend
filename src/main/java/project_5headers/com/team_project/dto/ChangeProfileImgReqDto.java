package project_5headers.com.team_project.dto;

import lombok.Data;
import project_5headers.com.team_project.entity.User;

@Data
public class ChangeProfileImgReqDto {
    private Integer userId;
    private String profileImg;

    public User toEntity() {
        User user = new User();
        user.setUserId(userId);
        user.setProfileImg(profileImg);
        return user;
    }
}
