package project_5headers.com.team_project.entity;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Estimate {
    private Integer estimateId;
    private Integer userId;
    private String title;
    private String purpose;
    private Integer budget;
    private Integer totalPrice;
    private Integer bookmarkCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}