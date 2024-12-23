package me.trongtin.project.service.product;

import lombok.RequiredArgsConstructor;
import me.trongtin.project.dto.ImageDTO;
import me.trongtin.project.dto.ProductDTO;
import me.trongtin.project.entity.Category;
import me.trongtin.project.entity.Product;
import me.trongtin.project.exception.AlreadyExistsException;
import me.trongtin.project.exception.ResourceNotFoundException;
import me.trongtin.project.repository.CategoryRepository;
import me.trongtin.project.repository.ImageRepository;
import me.trongtin.project.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;

    @Override
    public Product add(Product product) {
        productRepository.findByName(product.getName())
            .ifPresentOrElse(p -> {
                throw new AlreadyExistsException("Product id " + product.getId() + " already exists");
            }, () -> {
                Category category = categoryRepository.findByName(product.getCategory().getName())
                    .orElseGet(() -> {
                        Category newCategory = new Category(product.getCategory().getName());
                        return categoryRepository.save(newCategory);
                    });
                product.setCategory(category);
                productRepository.save(product);
            });
        return product;
    }

    @Override
    public Product update(Long id, Product newProduct) {
        return productRepository.findById(id)
                .map(existingProduct -> productRepository.save(updateExistingProduct(existingProduct, newProduct)))
                .orElseThrow(() -> new ResourceNotFoundException("Product id " + id + " not found"));
    }

    @Override
    public void delete(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete, () -> {
                    throw new ResourceNotFoundException("Product id " + id + " not found");
                });
    }

    @Override
    public Product get(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product id " + id + " not found"));
    }

    @Override
    public List<Product> getByName(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        if (products.isEmpty())
            throw new ResourceNotFoundException("No products found containing name " + name);
        return products;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    private Product updateExistingProduct(Product existingProduct, Product newProduct) {
        existingProduct.setName(newProduct.getName());
        existingProduct.setBrand(newProduct.getBrand());
        existingProduct.setPrice(newProduct.getPrice());
        existingProduct.setInventory(newProduct.getInventory());
        existingProduct.setDescription(newProduct.getDescription());
        existingProduct.setCategory(newProduct.getCategory());
        existingProduct.setAvailable(newProduct.isAvailable());
        existingProduct.setImages(newProduct.getImages());
        return existingProduct;
    }

    public ProductDTO mapper(Product product) {
        return ProductDTO.builder().
                id(product.getId()).
                name(product.getName()).
                brand(product.getBrand()).
                price(product.getPrice()).
                category(product.getCategory().getName()).
                inventory(product.getInventory()).
                description(product.getDescription()).
                available(product.isAvailable()).
                images(imageRepository.getImageDTOByProductId(product.getId())).
                build();
    }
}
