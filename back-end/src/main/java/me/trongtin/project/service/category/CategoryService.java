package me.trongtin.project.service.category;

import lombok.RequiredArgsConstructor;
import me.trongtin.project.dto.CategoryDTO;
import me.trongtin.project.entity.Category;
import me.trongtin.project.exception.AlreadyExistsException;
import me.trongtin.project.exception.ResourceNotFoundException;
import me.trongtin.project.repository.CategoryRepository;
import me.trongtin.project.service.product.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final IProductService productService;

    @Override
    public Category add(Category category) {
        Optional<Category> oCategory = categoryRepository.findByName(category.getName());
        if (oCategory.isPresent()) {
            throw new AlreadyExistsException("Category name " + category.getName() + " already exists");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category get(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category id " + id + " not found"));
    }

    @Override
    public Category getByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category name " + name + " not found"));
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category update(Long id, Category category) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(category.getName());
                    return categoryRepository.save(existingCategory);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category id " + id + " not found"));
    }

    @Override
    public void delete(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete, () -> {
                    throw new ResourceNotFoundException("Category id " + id + " not found");
                });
    }

    @Override
    public CategoryDTO mapper(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .products(category.getProducts().stream().map(productService::mapper).toList())
                .build();
    }
}
