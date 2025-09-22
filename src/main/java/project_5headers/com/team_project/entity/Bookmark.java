package project_5headers.com.team_project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark {
    private Integer bookmarkId;
    private Integer estimateId;
    private Integer userId;
    private String title;
    private String content;
    private LocalDateTime createDt;
}