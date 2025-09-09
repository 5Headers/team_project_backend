package project_5headers.com.team_project.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

        private Integer productId;
        private Integer estimateId;
        private String name;
        private String category;
        private Integer onlinePrice;
        private String onlineLink;
        private Integer offlinePrice;
        private String offlineStore;
        private String offlineLink;

}
