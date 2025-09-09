package project_5headers.com.team_project.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Bookmark {
        private Integer bookmarkId;
        private Integer estimateId;
        private Integer userId;
        private LocalDateTime createDt;
}
