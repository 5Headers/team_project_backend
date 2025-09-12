package project_5headers.com.team_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_5headers.com.team_project.dto.ApiRespDto;
import project_5headers.com.team_project.entity.Product;
import project_5headers.com.team_project.service.ProductService;
import project_5headers.com.team_project.security.model.PrincipalUser;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product, PrincipalUser principalUser) {
        return ResponseEntity.ok(productService.addProduct(product, principalUser));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Integer productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping("/estimate/{estimateId}")
    public ResponseEntity<?> getProductsByEstimate(@PathVariable Integer estimateId) {
        return ResponseEntity.ok(productService.getProductsByEstimateId(estimateId));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @PostMapping("/remove/{productId}")
    public ResponseEntity<?> removeProduct(@PathVariable Integer productId) {
        return ResponseEntity.ok(productService.removeProductById(productId));
    }
}
