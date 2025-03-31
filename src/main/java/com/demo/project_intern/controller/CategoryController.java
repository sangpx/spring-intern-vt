package com.demo.project_intern.controller;

import com.demo.project_intern.config.Translator;
import com.demo.project_intern.constant.EntityType;
import com.demo.project_intern.dto.CategoryDto;
import com.demo.project_intern.dto.request.category.CategoryCreateRequest;
import com.demo.project_intern.dto.request.category.CategoryUpdateRequest;
import com.demo.project_intern.dto.response.ResponseData;
import com.demo.project_intern.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.MessageUtil;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/category")
@Slf4j
@Tag(name = "Category Controller")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("")
    @Operation(method = "POST", summary = "Create Category", description = "API Create New Category")
    public ResponseData<CategoryDto> createCategory(@RequestBody @Valid CategoryCreateRequest request) {
        return ResponseData.<CategoryDto>builder()
                .message(Translator.getSuccessMessage("add", EntityType.CATEGORY))
                .data(categoryService.createCategory(request))
                .build();
    }

    @GetMapping("")
    @Operation(method = "GET", summary = "Get List Categories", description = "API Get List Categories")
    public ResponseData<List<CategoryDto>> getCategories() {
        return ResponseData.<List<CategoryDto>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.CATEGORY))
                .data(categoryService.getCategories())
                .build();
    }

    @GetMapping("/{categoryId}")
    @Operation(method = "GET", summary = "Get Detail Category", description = "API Get Detail Category")
    public ResponseData<CategoryDto> getCategory(@PathVariable("categoryId") Long categoryId) {
        return ResponseData.<CategoryDto>builder()
                .message(Translator.getSuccessMessage("getDetail", EntityType.CATEGORY))
                .data(categoryService.getCategory(categoryId))
                .build();
    }

    @PutMapping("/{categoryId}")
    @Operation(method = "PUT", summary = "Update Category", description = "API Update Category")
    public ResponseData<CategoryDto> updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody CategoryUpdateRequest request) {
        return ResponseData.<CategoryDto>builder()
                .message(Translator.getSuccessMessage("update", EntityType.CATEGORY))
                .data(categoryService.updateCategory(categoryId, request))
                .build();
    }

    @DeleteMapping("/{categoryId}")
    @Operation(method = "DELETE", summary = "Delete Category", description = "API Delete Category")
    public String deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return "Deleted Successfully!";
    }

    @GetMapping("/paging")
    @Operation(method = "GET", summary = "Get Paging Categories", description = "API Get Paging Categories")
    public ResponseData<Page<CategoryDto>> getPagingCategories(@RequestParam(required = false) String keyword,
                                                               @RequestParam(required = false) String code,
                                                               @RequestParam() int page,
                                                               @RequestParam() int size,
                                                               @RequestParam(defaultValue = "name") String sortBy,
                                                               @RequestParam(defaultValue = "asc") String direction) {
        return ResponseData.<Page<CategoryDto>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.CATEGORY))
                .data(categoryService.searchCategories(keyword, code, page, size, sortBy, direction))
                .build();
    }
}
