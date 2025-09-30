package project_5headers.com.team_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import project_5headers.com.team_project.entity.EstimatePart;

@Data
@AllArgsConstructor
public class EstimateRespDto {
    private Integer estimateId;   // 저장된 견적 ID
    private String data;          // GPT 텍스트
    private List<EstimatePart> parts; // 추천된 부품 목록
}
