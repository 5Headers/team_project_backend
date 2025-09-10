package project_5headers.com.team_project.dto.bookmark;


import lombok.AllArgsConstructor;
import lombok.Data;
import project_5headers.com.team_project.entity.Bookmark;

@Data
@AllArgsConstructor
public class AddBookmarkReqDto {
    private Integer estimateId;
    private Integer userId;

    public Bookmark toEntity() {
        return Bookmark.builder()
                .estimateId(estimateId)
                .userId(userId)
                .build();
    }
}
