package project_5headers.com.team_project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "part_tb")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer partId;

    private String name;   // 부품명 (예: "인텔 코어 i5-13400F")
    private String link;   // 실제 구매 링크
}

