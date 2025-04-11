package com.demo.project_intern.controller;

import com.demo.project_intern.config.Translator;
import com.demo.project_intern.constant.EntityType;
import com.demo.project_intern.dto.BorrowBookDto;
import com.demo.project_intern.dto.CategoryDto;
import com.demo.project_intern.dto.request.borrowBook.BorrowBookCreateRequest;
import com.demo.project_intern.dto.request.borrowBook.BorrowBookSearchRequest;
import com.demo.project_intern.dto.request.borrowBook.BorrowBookUpdateRequest;
import com.demo.project_intern.dto.request.category.CategorySearchRequest;
import com.demo.project_intern.dto.response.ResponseData;
import com.demo.project_intern.service.BorrowBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/borrowBook")
@Slf4j
@Tag(name = "BorrowBook Controller")
@RequiredArgsConstructor
public class BorrowBookController {

    private final BorrowBookService borrowBookService;

    @PostMapping("")
    @Operation(method = "POST", summary = "Create BorrowBook", description = "API Create New BorrowBook")
    public ResponseData<BorrowBookDto> createBorrowBook(@RequestBody @Valid BorrowBookCreateRequest request) {
        return ResponseData.<BorrowBookDto>builder()
                .message(Translator.getSuccessMessage("add", EntityType.BORROW_BOOK))
                .data(borrowBookService.createBorrowBook(request))
                .build();
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("")
    @Operation(method = "GET", summary = "Get List BorrowBooks", description = "API Get List BorrowBooks")
    public ResponseData<List<BorrowBookDto>> getBorrowBooks() {
        return ResponseData.<List<BorrowBookDto>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.BORROW_BOOK))
                .data(borrowBookService.getBorrowBooks())
                .build();
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/{borrowBookId}")
    @Operation(method = "GET", summary = "Get Detail BorrowBook", description = "API Get Detail BorrowBook")
    public ResponseData<BorrowBookDto> getBorrowBook(@PathVariable("borrowBookId") Long borrowBookId) {
        return ResponseData.<BorrowBookDto>builder()
                .message(Translator.getSuccessMessage("getDetail", EntityType.BORROW_BOOK))
                .data(borrowBookService.getBorrowBook(borrowBookId))
                .build();
    }

    @PutMapping("/{borrowBookId}")
    @Operation(method = "PUT", summary = "Update BorrowBook", description = "API Update BorrowBook")
    public ResponseData<BorrowBookDto> updateBorrowBook(@PathVariable("borrowBookId") Long borrowBookId, @RequestBody BorrowBookUpdateRequest request) {
        return ResponseData.<BorrowBookDto>builder()
                .message(Translator.getSuccessMessage("update", EntityType.BORROW_BOOK))
                .data(borrowBookService.updateBorrowBook(borrowBookId, request))
                .build();
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @DeleteMapping("/{borrowBookId}")
    @Operation(method = "DELETE", summary = "Delete BorrowBook", description = "API Delete BorrowBook")
    public ResponseData<Void> deleteBorrowBook(@PathVariable("borrowBookId") Long borrowBookId) {
        borrowBookService.deleteBorrowBook(borrowBookId);
        return ResponseData.<Void>builder()
                .message(Translator.getSuccessMessage("delete", EntityType.BORROW_BOOK))
                .build();
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping("/paging")
    @Operation(method = "POST", summary = "Get Paging BorrowBooks", description = "API Get Paging BorrowBooks")
    public ResponseData<Page<BorrowBookDto>> getPagingBorrowBooks(@RequestBody BorrowBookSearchRequest request) {
        return ResponseData.<Page<BorrowBookDto>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.BORROW_BOOK))
                .data(borrowBookService.search(request))
                .build();
    }
}