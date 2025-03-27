package com.demo.project_intern.controller;

import com.demo.project_intern.config.Translator;
import com.demo.project_intern.constant.EntityType;
import com.demo.project_intern.dto.BookDto;
import com.demo.project_intern.dto.request.book.BookCreateRequest;
import com.demo.project_intern.dto.request.book.BookUpdateRequest;
import com.demo.project_intern.dto.response.ResponseData;
import com.demo.project_intern.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/book")
@Slf4j
@Tag(name = "Book Controller")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("")
    @Operation(method = "POST", summary = "Create Book", description = "API Create New Book")
    public ResponseData<BookDto> createBook(@RequestBody @Valid BookCreateRequest request) {
        return ResponseData.<BookDto>builder()
                .message(Translator.getSuccessMessage("add", EntityType.BOOK))
                .data(bookService.createBook(request))
                .build();
    }

    @GetMapping("")
    @Operation(method = "GET", summary = "Get List Books", description = "API Get List Books")
    public ResponseData<List<BookDto>> getBooks() {
        return ResponseData.<List<BookDto>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.BOOK))
                .data(bookService.getBooks())
                .build();
    }

    @GetMapping("/{bookId}")
    @Operation(method = "GET", summary = "Get Detail Book", description = "API Get Detail Book")
    public ResponseData<BookDto> getBook(@PathVariable("bookId") Long bookId) {
        return ResponseData.<BookDto>builder()
                .message(Translator.getSuccessMessage("getDetail", EntityType.BOOK))
                .data(bookService.getBook(bookId))
                .build();
    }

    @PutMapping("/{bookId}")
    @Operation(method = "PUT", summary = "Update Book", description = "API Update Book")
    public ResponseData<BookDto> updateBook(@PathVariable("bookId") Long bookId, @RequestBody BookUpdateRequest request) {
        return ResponseData.<BookDto>builder()
                .message(Translator.getSuccessMessage("update", EntityType.BOOK))
                .data(bookService.updateBook(bookId, request))
                .build();
    }

    @DeleteMapping("/{bookId}")
    @Operation(method = "DELETE", summary = "Delete Book", description = "API Delete Book")
    public String deleteBook(@PathVariable("bookId") Long bookId) {
        bookService.deleteBook(bookId);
        return "Deleted successfully!";
    }

    @GetMapping("/paging")
    @Operation(method = "GET", summary = "Get Paging Books", description = "API Get Paging Books")
    public ResponseData<Page<BookDto>> getPagingBooks(@RequestParam(required = false) String keyword,
                                                               @RequestParam(required = false) String code,
                                                               @RequestParam() int page,
                                                               @RequestParam() int size,
                                                               @RequestParam(defaultValue = "code") String sortBy,
                                                               @RequestParam(defaultValue = "asc") String direction) {
        return ResponseData.<Page<BookDto>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.BOOK))
                .data(bookService.searchBooks(keyword, code, page, size, sortBy, direction))
                .build();
    }
}
