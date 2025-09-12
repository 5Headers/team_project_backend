package project_5headers.com.team_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project_5headers.com.team_project.dto.ApiRespDto;
import project_5headers.com.team_project.entity.Product;
import project_5headers.com.team_project.repository.ProductRepository;
import project_5headers.com.team_project.security.model.PrincipalUser;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> addProduct(Product product, PrincipalUser principalUser) {
        // 필요 시 권한 체크
        int result = productRepository.addProduct(product);
        return result == 1
                ? new ApiRespDto<>("success", "상품이 추가되었습니다.", product)
                : new ApiRespDto<>("failed", "상품 추가 실패", null);
    }

    public ApiRespDto<?> getProductById(Integer productId) {
        Optional<Product> optional = productRepository.getProductById(productId);
        return optional.isPresent()
                ? new ApiRespDto<>("success", "조회 성공", optional.get())
                : new ApiRespDto<>("failed", "상품이 존재하지 않습니다.", null);
    }

    public ApiRespDto<?> getProductsByEstimateId(Integer estimateId) {
        List<Product> list = productRepository.getProductsByEstimateId(estimateId);
        return new ApiRespDto<>("success", "조회 성공", list);
    }

    public ApiRespDto<?> getProductsByCategory(String category) {
        List<Product> list = productRepository.getProductsByCategory(category);
        return new ApiRespDto<>("success", "조회 성공", list);
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> updateProduct(Product product) {
        int result = productRepository.updateProduct(product);
        return result == 1
                ? new ApiRespDto<>("success", "상품이 수정되었습니다.", product)
                : new ApiRespDto<>("failed", "상품 수정 실패", null);
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> removeProductById(Integer productId) {
        int result = productRepository.removeProductById(productId);
        return result == 1
                ? new ApiRespDto<>("success", "상품이 삭제되었습니다.", null)
                : new ApiRespDto<>("failed", "상품 삭제 실패", null);
    }
}
