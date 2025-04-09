package com.demo.project_intern.service;

import com.demo.project_intern.dto.CategoryBookCountDto;
import com.demo.project_intern.dto.CategoryDto;
import com.demo.project_intern.dto.request.category.CategoryCreateRequest;
import com.demo.project_intern.dto.request.category.CategorySearchRequest;
import com.demo.project_intern.dto.request.category.CategoryUpdateRequest;
import com.demo.project_intern.dto.request.user.UserSearchRequest;

import java.util.List;

public interface CategoryService extends GenericSearchService<CategorySearchRequest, CategoryDto> {
    CategoryDto createCategory(CategoryCreateRequest request);
    List<CategoryDto> getCategories();
    CategoryDto getCategory(Long categoryId);
    CategoryDto updateCategory(Long categoryId, CategoryUpdateRequest request);
    void deleteCategory(Long categoryId);
    List<CategoryBookCountDto> getCategoryBookCounts();
}
