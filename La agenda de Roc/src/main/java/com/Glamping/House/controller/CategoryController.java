package com.Glamping.House.controller;


import com.Glamping.House.model.Category;
import com.Glamping.House.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {

        return ResponseEntity.ok(categoryService.addCategory(category));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> search(@PathVariable Integer id) {
        Category category = categoryService.search(id).orElse(null);

        return ResponseEntity.ok(category);
    }

    @PutMapping()
    public ResponseEntity<Category> editCategory(@RequestBody Category category) {
        ResponseEntity<Category> response = null;

        if (category.getId() != null && categoryService.search(category.getId()).isPresent())
            response = ResponseEntity.ok(categoryService.editCategory(category));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        ResponseEntity<String> response = null;

        if (categoryService.search(id).isPresent()) {
            categoryService.deleteCategory(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return response;
    }
    @GetMapping
    public ResponseEntity<List<Category>> listAllCategories(){

        return ResponseEntity.ok(categoryService.listAllCategories());
    }



}
