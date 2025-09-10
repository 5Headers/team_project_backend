package project_5headers.com.team_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiRespDto<T> {
    private String status;   // success / failed
    private String message;  // 메시지
    private T data;          // 응답 데이터
}
