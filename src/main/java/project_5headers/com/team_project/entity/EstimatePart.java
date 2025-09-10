package project_5headers.com.team_project.entity;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EstimatePart {
    private Integer estimatePartId;
    private Integer estimateId;
    private String category;
    private String name;
    private Integer price;
    private String link;
    private String storeType;
    private LocalDateTime createdAt;
}
