package project_5headers.com.team_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_5headers.com.team_project.entity.EstimatePart;
import project_5headers.com.team_project.repository.EstimatePartRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EstimatePartService {

    @Autowired
    private EstimatePartRepository estimatePartRepository;

    // 부품 추가
    public EstimatePart addEstimatePart(EstimatePart estimatePart) {
        estimatePartRepository.addEstimatePart(estimatePart);
        return estimatePart;
    }

    // ID로 부품 조회
    public Optional<EstimatePart> getEstimatePartById(Integer estimatePartId) {
        return estimatePartRepository.getEstimatePartById(estimatePartId);
    }

    // 견적 ID로 부품 목록 조회
    public List<EstimatePart> getPartsByEstimateId(Integer estimateId) {
        return estimatePartRepository.getPartsByEstimateId(estimateId);
    }

    // 부품 수정
    public EstimatePart updateEstimatePart(EstimatePart estimatePart) {
        estimatePartRepository.updateEstimatePart(estimatePart);
        return estimatePart;
    }

    // 부품 삭제
    public boolean removeEstimatePartById(Integer estimatePartId) {
        return estimatePartRepository.removeEstimatePartById(estimatePartId) > 0;
    }
}
