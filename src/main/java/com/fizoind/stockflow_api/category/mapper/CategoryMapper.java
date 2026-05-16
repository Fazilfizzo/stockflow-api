package com.fizoind.stockflow_api.category.mapper;

import com.fizoind.stockflow_api.category.dto.CategoryRequestDTO;
import com.fizoind.stockflow_api.category.dto.CategoryResponseDTO;
import com.fizoind.stockflow_api.category.entity.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category();
        category.setName(categoryRequestDTO.getName());
        category.setDescription(categoryRequestDTO.getDescription());
        return category;
    }

    public static CategoryResponseDTO toDto(Category category) {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(category.getId());
        categoryResponseDTO.setName(category.getName());
        categoryResponseDTO.setDescription(category.getDescription());
        return categoryResponseDTO;
    }
}
