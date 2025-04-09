package com.demo.project_intern.service.impl;

import com.demo.project_intern.constant.ErrorCode;
import com.demo.project_intern.dto.CategoryBookCountDto;
import com.demo.project_intern.dto.CategoryDto;
import com.demo.project_intern.dto.request.category.CategoryCreateRequest;
import com.demo.project_intern.dto.request.category.CategorySearchRequest;
import com.demo.project_intern.dto.request.category.CategoryUpdateRequest;
import com.demo.project_intern.entity.CategoryEntity;
import com.demo.project_intern.exception.BaseLibraryException;
import com.demo.project_intern.repository.CategoryRepository;
import com.demo.project_intern.service.CategoryService;
import com.demo.project_intern.utils.PageableUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;


    @Override
    public CategoryDto createCategory(CategoryCreateRequest request) {
        CategoryEntity categoryEntity = mapper.map(request, CategoryEntity.class);
        if(categoryRepository.existsByCode(request.getCode())) {
            throw new BaseLibraryException(ErrorCode.CATEGORY_EXISTED);
        }
        categoryRepository.save(categoryEntity);
        return mapper.map(categoryEntity, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryEntity -> mapper.map(categoryEntity, CategoryDto.class))
                .toList();
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        return mapper.map(categoryEntity, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryUpdateRequest request) {
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setUpdatedAt(LocalDate.now());
        categoryRepository.save(category);
        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryBookCountDto> getCategoryBookCounts() {
        return categoryRepository.countBooksByCategory();
    }

    @Override
    public Page<CategoryDto> search(CategorySearchRequest request) {
        Pageable pageable = PageableUtils.from(request);
        return categoryRepository.search(request, pageable);
    }
}
