package com.demo.project_intern.service;

import com.demo.project_intern.dto.BookDto;
import com.demo.project_intern.dto.request.book.AssignRemoveCategoryRequest;
import com.demo.project_intern.dto.request.book.BookCreateRequest;
import com.demo.project_intern.dto.request.book.BookSearchRequest;
import com.demo.project_intern.dto.request.book.BookUpdateRequest;
import com.demo.project_intern.dto.request.user.AssignRemoveRolesRequest;
import com.demo.project_intern.dto.response.AssignCategoryResponse;
import com.demo.project_intern.dto.response.AssignRoleResponse;
import com.demo.project_intern.dto.response.RemoveCategoryResponse;
import com.demo.project_intern.dto.response.RemoveRoleResponse;
import org.springframework.data.domain.Page;

import java.io.ByteArrayOutputStream;
import java.util.List;

public interface BookService extends GenericSearchService<BookSearchRequest, BookDto> {
    BookDto createBook(BookCreateRequest request);
    List<BookDto> getBooks();
    BookDto getBook(Long bookId);
    BookDto updateBook(Long bookId, BookUpdateRequest request);
    void deleteBook(Long bookId);
    ByteArrayOutputStream exportBook(String name);
    AssignCategoryResponse assignCategories (AssignRemoveCategoryRequest request);
    RemoveCategoryResponse removeCategories (AssignRemoveCategoryRequest request);
}