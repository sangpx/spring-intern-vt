package com.demo.project_intern.service;

import com.demo.project_intern.dto.CategoryDto;
import com.demo.project_intern.dto.request.category.CategoryCreateRequest;
import com.demo.project_intern.dto.request.category.CategoryUpdateRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryCreateRequest request);
    List<CategoryDto> getCategories();
    CategoryDto getCategory(Long categoryId);
    CategoryDto updateCategory(Long categoryId, CategoryUpdateRequest request);
    void deleteCategory(Long categoryId);
//    Page<CategoryDto> searchCategories (String keyword, String code, int page, int size, String sortBy, String direction);
}
