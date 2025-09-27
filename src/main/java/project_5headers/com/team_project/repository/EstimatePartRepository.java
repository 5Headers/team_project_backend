package project_5headers.com.team_project.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project_5headers.com.team_project.entity.EstimatePart;
import project_5headers.com.team_project.mapper.EstimatePartMapper;

import java.util.List;
import java.util.Optional;

@Repository

public class EstimatePartRepository {

    @Autowired
    private EstimatePartMapper estimatePartMapper;

    public int addEstimatePart(EstimatePart estimatePart){
        return estimatePartMapper.addEstimatePart(estimatePart);
    }

    public Optional<EstimatePart> getEstimatePartById(Integer estimatePartId){
        return estimatePartMapper.getEstimatePartById(estimatePartId);
    }

    public List<EstimatePart> getPartsByEstimateId(Integer estimateId) {
        return estimatePartMapper.getEstimatePartsByEstimateId(estimateId);
    }

    public int updateEstimatePart(EstimatePart estimatePart){
        return estimatePartMapper.updateEstimatePart(estimatePart);
    }

    public int removeEstimatePartById(Integer estimateId){
        return estimatePartMapper.removeEstimatePartById(estimateId);
    }
    public List<EstimatePart> getAllParts() {
        return estimatePartMapper.getAllParts();
    }
}
