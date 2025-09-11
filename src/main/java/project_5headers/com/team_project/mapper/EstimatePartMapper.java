package project_5headers.com.team_project.mapper;

import project_5headers.com.team_project.entity.EstimatePart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EstimatePartMapper {

    int addEstimatePart(EstimatePart estimatePart);
    Optional<EstimatePart> getEstimatePartById(Integer estimatePartId);
    List<EstimatePart> getEstimatePartsByEstimateId(Integer estimateId);
    int updateEstimatePart(EstimatePart estimatePart);
    int removeEstimatePartById(Integer estimatePartId);
}
