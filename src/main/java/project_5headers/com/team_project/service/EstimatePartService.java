package project_5headers.com.team_project.service;


import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_5headers.com.team_project.entity.EstimatePart;
import project_5headers.com.team_project.repository.EstimatePartRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EstimatePartService {

    @Autowired
    private EstimatePartRepository repository;

    // -----------부품 추가하기--------------
    public EstimatePart addPart(EstimatePart part) {
        repository.addEstimatePart(part);
        return part;
    }

    // -----------단일 부품 추가하기--------------
    public Optional<EstimatePart> getPartById(Integer partId) {
        return repository.getEstimatePartById(partId);
    }

    // -----------특정 견적 부품 전체 조회하기--------------
    public List<EstimatePart> getPartsByEstimate(Integer estimateId) {
        return repository.getPartsByEstimateId(estimateId);
    }

    // -----------부품 수정하기--------------
    public EstimatePart updatePart(EstimatePart part) {
        repository.updateEstimatePart(part);
        return part;
    }

    // -----------부품 삭제하기--------------
    public void removePart(Integer partId) {
        repository.removeEstimatePartById(partId);
    }

    // -----------추가 로직 예시: 특정 견적의 전체 가격 합계--------------
    public int getTotalPriceByEstimate(Integer estimateId) {
        List<EstimatePart> parts = repository.getPartsByEstimateId(estimateId);
        return parts.stream().mapToInt(EstimatePart::getPrice).sum();
    }

}
