package com.Glamping.House.service;

import com.Glamping.House.model.Category;
import com.Glamping.House.repository.impl.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
     public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);

    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }

    public Optional<Category> search(Integer id) {
        return categoryRepository.findById(id);
    }

    public List<Category> listAllCategories() {
        return categoryRepository.findAll();
    }

    public Category editCategory(Category category) {
        return categoryRepository.save(category);
    }
}


