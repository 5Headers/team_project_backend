package project_5headers.com.team_project.mapper;

import project_5headers.com.team_project.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductMapper {

    int addProduct(Product product);
    Optional<Product> getProductById(Integer productId);
    List<Product> getProductsByEstimateId(Integer estimateId);
    List<Product> getProductsByCategory(String category);
    int updateProduct(Product product);
    int removeProductById(Integer productId);
}
