package project_5headers.com.team_project.mapper;

import org.apache.ibatis.annotations.Mapper;
import project_5headers.com.team_project.entity.Estimate;

import java.util.List;
import java.util.Optional;


@Mapper

public interface EstimateMapper {
    int insertEstimate(Estimate estimate);
    Optional<Estimate> selectEstimateById(Integer estimateId);
    List<Estimate> selectAllEstimate();
    int updateEstimate(Estimate estimate);
    int deleteEstimate(Integer estimateId);


}
