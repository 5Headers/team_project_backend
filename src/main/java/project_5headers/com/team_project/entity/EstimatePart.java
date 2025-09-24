package project_5headers.com.team_project.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EstimatePart {
    private Integer estimatePartId; // PK
    private Integer estimateId;     // FK
    private String category;        // CPU, GPU, RAM ...
    private String name;            // 제품명
    private Integer price;          // 가격
    private String link;            // 최저가 링크
    private String storeType;       // ONLINE / OFFLINE 구분
    private LocalDateTime createdAt;
}
