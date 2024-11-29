package me.trongtin.project.controller.rest;

import lombok.RequiredArgsConstructor;
import me.trongtin.project.dto.ProductDTO;
import me.trongtin.project.entity.Product;
import me.trongtin.project.response.ApiResponse;
import me.trongtin.project.service.product.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductRestController {

    private final IProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProducts() {
        List<ProductDTO> products = productService.getAll().stream().map(productService::mapper).toList();
        return ResponseEntity.ok(new ApiResponse<>("Get all products", products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>("Get product id " + id, productService.mapper(productService.get(id))));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductByName(@PathVariable String name) {
        return ResponseEntity.ok(new ApiResponse<>("Get product name " + name, productService.mapper(productService.getByName(name))));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> addProduct(@RequestBody Product product) {
        Product createdProduct = productService.add(product);
        return ResponseEntity.ok(new ApiResponse<>("Added product " + createdProduct.getId(), productService.mapper(createdProduct)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return ResponseEntity.ok(new ApiResponse<>("Updated product " + id, productService.mapper(productService.update(id, product))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>("Deleted product " + id, null));
    }

}
