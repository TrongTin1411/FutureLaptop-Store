package me.trongtin.project.controller.rest;

import lombok.RequiredArgsConstructor;
import me.trongtin.project.dto.CategoryDTO;
import me.trongtin.project.entity.Category;
import me.trongtin.project.service.category.ICategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryRestController {

    private final ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryService.getAll();
        return ResponseEntity.ok(categories.stream().map(categoryService::mapper).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.mapper(categoryService.get(id)));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.mapper(categoryService.getByName(name)));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody Category category) {
        Category newCategory = categoryService.add(category);
        return ResponseEntity.ok(categoryService.mapper(newCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateProduct(@PathVariable Long id, @RequestBody Category category) {
        Category updateCategory = categoryService.update(id, category);
        return ResponseEntity.ok(categoryService.mapper(updateCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }

}
