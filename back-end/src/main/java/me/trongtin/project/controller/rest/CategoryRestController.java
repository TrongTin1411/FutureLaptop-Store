package me.trongtin.project.controller.rest;

import lombok.RequiredArgsConstructor;
import me.trongtin.project.entity.Category;
import me.trongtin.project.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        List<Category> categories = categoryService.getAll();
        return ResponseEntity.ok(new ApiResponse<>("Get all categories", categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>("Get category id '" + id + "'", categoryService.get(id)));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<Category>> getCategoryByName(@PathVariable String name) {
        return ResponseEntity.ok(new ApiResponse<>("Get category name '" + name + "'", categoryService.getByName(name)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> addCategory(@RequestBody Category category) {
        return ResponseEntity.ok(new ApiResponse<>("Added category '" + category.getName() + "'", categoryService.add(category)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> updateProduct(@PathVariable Long id, @RequestBody Category category) {
        category.setId(id);
        return ResponseEntity.ok(new ApiResponse<>("Updated category '" + id + "'", categoryService.update(id, category)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>("Deleted category '" + id + "'", null));
    }

}
