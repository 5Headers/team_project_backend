package project_5headers.com.team_project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GptEstimateRequest {
    private String title;
    private String purpose;
    private int cost;
}

