package project_5headers.com.team_project.mapper;

import project_5headers.com.team_project.entity.Estimate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EstimateMapper {

    int addEstimate(Estimate estimate);
    Optional<Estimate> getEstimateById(Integer estimateId);
    List<Estimate> getEstimateList();
    List<Estimate> getEstimatesByUserId(Integer userId);
    int updateEstimate(Estimate estimate);
    int removeEstimateById(Integer estimateId);
}
