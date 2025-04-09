package com.demo.project_intern.service;

import com.demo.project_intern.dto.BookDto;
import com.demo.project_intern.dto.CategoryBookCountDto;
import com.demo.project_intern.dto.request.book.*;
import com.demo.project_intern.dto.request.user.AssignRemoveRolesRequest;
import com.demo.project_intern.dto.response.AssignCategoryResponse;
import com.demo.project_intern.dto.response.AssignRoleResponse;
import com.demo.project_intern.dto.response.RemoveCategoryResponse;
import com.demo.project_intern.dto.response.RemoveRoleResponse;
import com.demo.project_intern.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public interface BookService extends GenericSearchService<BookSearchRequest, BookDto> {
    BookDto createBook(BookCreateRequest request);
    List<BookDto> getBooks();
    BookDto getBook(Long bookId);
    BookDto updateBook(Long bookId, BookUpdateRequest request);
    void deleteBook(Long bookId);
    ByteArrayInputStream importBook(MultipartFile file);
    ByteArrayOutputStream exportBook(String name);
    AssignCategoryResponse assignCategories (AssignRemoveCategoryRequest request);
    RemoveCategoryResponse removeCategories (AssignRemoveCategoryRequest request);
    List<BookDto> getBorrowedBooksByUser(Long userId);
}