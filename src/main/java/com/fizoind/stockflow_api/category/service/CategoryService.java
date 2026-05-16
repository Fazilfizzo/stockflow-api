package com.fizoind.stockflow_api.category.service;

import com.fizoind.stockflow_api.category.dto.CategoryRequestDTO;
import com.fizoind.stockflow_api.category.dto.CategoryResponseDTO;
import com.fizoind.stockflow_api.category.entity.Category;
import com.fizoind.stockflow_api.category.mapper.CategoryMapper;
import com.fizoind.stockflow_api.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = CategoryMapper.toEntity(categoryRequestDTO);
        Category saved_category = categoryRepository.save(category);
        return CategoryMapper.toDto(saved_category);
    }

    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDto)
                .toList();
    }

    public CategoryResponseDTO getCategoryById(Long category_id) {
        Category category = categoryRepository.findById(category_id).orElseThrow(() -> new RuntimeException("Category does not exist"));
        return CategoryMapper.toDto(category);
    }

    public void deleteCategory(Long category_id) {
        categoryRepository.delete(categoryRepository.findById(category_id).orElseThrow(() -> new RuntimeException("Category does not exist")));
    }
}
