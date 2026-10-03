package com.fizoind.stockflow_api.category.controller;

import com.fizoind.stockflow_api.category.dto.CategoryRequestDTO;
import com.fizoind.stockflow_api.category.entity.Category;
import com.fizoind.stockflow_api.category.service.CategoryService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CategoryGraphQLController {

    private final CategoryService categoryService;

    public CategoryGraphQLController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @MutationMapping
    public Category createCategory(@Argument CategoryRequestDTO input) {
        return categoryService.createCategory_graphql(input);
    }
}
