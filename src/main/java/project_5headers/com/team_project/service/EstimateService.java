package project_5headers.com.team_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project_5headers.com.team_project.dto.ApiRespDto;
import project_5headers.com.team_project.entity.Estimate;
import project_5headers.com.team_project.repository.EstimateRepository;
import project_5headers.com.team_project.security.model.PrincipalUser;

import java.util.List;
import java.util.Optional;

@Service
public class EstimateService {

    @Autowired
    private EstimateRepository estimateRepository;

    // ----------------------- Add Estimate -----------------------
    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> addEstimate(Estimate estimate, PrincipalUser principalUser) {
        if (!estimate.getUserId().equals(principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "권한이 없습니다.", null);
        }
        int result = estimateRepository.addEstimate(estimate);
        return result == 1
                ? new ApiRespDto<>("success", "견적이 추가되었습니다.", estimate)
                : new ApiRespDto<>("failed", "견적 추가 실패", null);
    }

    // ----------------------- Get Estimate By Id -----------------------
    public ApiRespDto<?> getEstimateById(Integer estimateId) {
        Optional<Estimate> optionalEstimate = estimateRepository.getEstimateById(estimateId);
        return optionalEstimate.isPresent()
                ? new ApiRespDto<>("success", "조회 성공", optionalEstimate.get())
                : new ApiRespDto<>("failed", "견적이 존재하지 않습니다.", null);
    }

    // ----------------------- Get Estimates By User -----------------------
    public ApiRespDto<?> getEstimatesByUserId(Integer userId, PrincipalUser principalUser) {
        if (!userId.equals(principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "권한이 없습니다.", null);
        }
        List<Estimate> list = estimateRepository.getEstimatesByUserId(userId);
        return new ApiRespDto<>("success", "조회 성공", list);
    }

    // ----------------------- Get All Estimates -----------------------
    public ApiRespDto<?> getEstimateList() {
        List<Estimate> list = estimateRepository.getEstimateList();
        return new ApiRespDto<>("success", "전체 견적 조회 성공", list);
    }

    // ----------------------- Update Estimate -----------------------
    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> updateEstimate(Estimate estimate, PrincipalUser principalUser) {
        Optional<Estimate> optionalEstimate = estimateRepository.getEstimateById(estimate.getEstimateId());
        if (optionalEstimate.isEmpty()) {
            return new ApiRespDto<>("failed", "견적이 존재하지 않습니다.", null);
        }
        if (!optionalEstimate.get().getUserId().equals(principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "수정 권한이 없습니다.", null);
        }

        int result = estimateRepository.updateEstimate(estimate);
        return result == 1
                ? new ApiRespDto<>("success", "견적이 수정되었습니다.", estimate)
                : new ApiRespDto<>("failed", "수정 실패", null);
    }

    // ----------------------- Remove Estimate -----------------------
    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> removeEstimateById(Integer estimateId, PrincipalUser principalUser) {
        Optional<Estimate> optionalEstimate = estimateRepository.getEstimateById(estimateId);
        if (optionalEstimate.isEmpty()) {
            return new ApiRespDto<>("failed", "견적이 존재하지 않습니다.", null);
        }
        if (!optionalEstimate.get().getUserId().equals(principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "삭제 권한이 없습니다.", null);
        }
        int result = estimateRepository.removeEstimateById(estimateId);
        return result == 1
                ? new ApiRespDto<>("success", "견적이 삭제되었습니다.", null)
                : new ApiRespDto<>("failed", "삭제 실패", null);
    }
}