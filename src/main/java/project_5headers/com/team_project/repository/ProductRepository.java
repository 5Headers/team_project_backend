package project_5headers.com.team_project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project_5headers.com.team_project.entity.Product;
import project_5headers.com.team_project.mapper.ProductMapper;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    @Autowired
    private ProductMapper productMapper;

    public int addProduct(Product product) {
        return productMapper.addProduct(product);
    }

    public Optional<Product> getProductById(Integer productId) {
        return productMapper.getProductById(productId);
    }

    public List<Product> getProductsByEstimateId(Integer estimateId) {
        return productMapper.getProductsByEstimateId(estimateId);
    }

    public List<Product> getProductsByCategory(String category) {
        return productMapper.getProductsByCategory(category);
    }

    public int updateProduct(Product product) {
        return productMapper.updateProduct(product);
    }

    public int removeProductById(Integer productId) {
        return productMapper.removeProductById(productId);
    }
}
