package project_5headers.com.team_project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project_5headers.com.team_project.entity.Estimate;
import project_5headers.com.team_project.mapper.EstimateMapper;

import java.util.List;
import java.util.Optional;

@Repository
public class EstimateRepository {

    @Autowired
    private EstimateMapper estimateMapper;

    // 새로운 Estimate 추가
    public int addEstimate(Estimate estimate) {
        return estimateMapper.addEstimate(estimate);
    }

    // ID로 Estimate 조회
    public Optional<Estimate> getEstimateById(Integer estimateId) {
        return estimateMapper.getEstimateById(estimateId);
    }

    // 전체 Estimate 리스트 조회
    public List<Estimate> getEstimateList() {
        return estimateMapper.getEstimateList();
    }

    // 사용자별 Estimate 조회
    public List<Estimate> getEstimatesByUserId(Integer userId) {
        return estimateMapper.getEstimatesByUserId(userId);
    }

    // Estimate 수정
    public int updateEstimate(Estimate estimate) {
        return estimateMapper.updateEstimate(estimate);
    }

    // Estimate 삭제
    public int removeEstimateById(Integer estimateId) {
        return estimateMapper.removeEstimateById(estimateId);
    }
}