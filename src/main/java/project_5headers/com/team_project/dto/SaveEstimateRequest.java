package project_5headers.com.team_project.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveEstimateRequest {
    private Long userId;
    private String title;
    private String purpose;
    private Integer budget;
    private Integer totalPrice;
    private List<PartDto> parts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PartDto {
        private String category;
        private String name;
        private Integer price;
        private String link;
        private String storeType;
    }
}