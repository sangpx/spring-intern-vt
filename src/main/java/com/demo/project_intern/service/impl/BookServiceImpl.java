package com.demo.project_intern.service.impl;

import com.demo.project_intern.constant.ErrorCode;
import com.demo.project_intern.dto.BookDto;
import com.demo.project_intern.dto.CategoryDto;
import com.demo.project_intern.dto.PermissionDto;
import com.demo.project_intern.dto.request.book.BookCreateRequest;
import com.demo.project_intern.dto.request.book.BookSearchRequest;
import com.demo.project_intern.dto.request.book.BookUpdateRequest;
import com.demo.project_intern.entity.BookEntity;
import com.demo.project_intern.entity.CategoryEntity;
import com.demo.project_intern.entity.PermissionEntity;
import com.demo.project_intern.exception.BaseLibraryException;
import com.demo.project_intern.repository.BookRepository;
import com.demo.project_intern.repository.CategoryRepository;
import com.demo.project_intern.service.BookService;
import com.demo.project_intern.service.GenericSearchService;
import com.demo.project_intern.utils.PageableUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;


    @Override
    public BookDto createBook(BookCreateRequest request) {
        if(bookRepository.findByCode(request.getCode()).isPresent()) {
            throw new BaseLibraryException(ErrorCode.BOOK_EXISTED);
        }
        BookEntity bookEntity = mapper.map(request, BookEntity.class);
        Set<CategoryEntity> categoryEntities = getCategoryEntity(request.getCategories());
        bookEntity.setCategories(categoryEntities.isEmpty() ? Collections.emptySet() : categoryEntities);
        bookRepository.save(bookEntity);
        return mapper.map(bookEntity, BookDto.class);
    }

    @Override
    public List<BookDto> getBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookEntity -> mapper.map(bookEntity, BookDto.class))
                .toList();
    }

    @Override
    public BookDto getBook(Long bookId) {
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        return mapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookDto updateBook(Long bookId, BookUpdateRequest request) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        book.setTitle(request.getTitle());
        book.setDescription(request.getDescription());
        book.setAuthor(request.getAuthor());
        book.setPublisher(request.getPublisher());
        book.setPublishedYear(request.getPublishedYear());
        book.setUpdatedAt(LocalDate.now());
        bookRepository.save(book);
        return mapper.map(book, BookDto.class);
    }

    @Override
    public void deleteBook(Long bookId) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        bookRepository.delete(book);
    }

    private Set<CategoryEntity> getCategoryEntity (Set<CategoryDto> categoryDtos) {
        // Kiểm tra request.getCategories() có null không
        Set<String> categoryCodes = Optional.ofNullable(categoryDtos)
                .orElse(Collections.emptySet())
                .stream()
                .map(CategoryDto::getCode)
                .collect(Collectors.toSet());

        if (categoryCodes.isEmpty()) {
            throw new BaseLibraryException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        // Tìm danh mục có trong DB
        List<CategoryEntity> categoryEntities = categoryRepository.findByCodeIn(categoryCodes);

        // Kiểm tra danh mục nào không tìm thấy
        Set<String> foundCodes = categoryEntities.stream()
                .map(CategoryEntity::getCode)
                .collect(Collectors.toSet());

        Set<String> missingCategories = new HashSet<>(categoryCodes);
        missingCategories.removeAll(foundCodes);

        // Nếu có danh mục không tồn tại trong DB → bắn exception
        if (!missingCategories.isEmpty()) {
            throw new BaseLibraryException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        //Return list category hợp lệ
        return new HashSet<>(categoryEntities);
    }

    @Override
    public Page<BookDto> search(BookSearchRequest request) {
        Pageable pageable = PageableUtils.from(request);
        return bookRepository.search(request, pageable);
    }
}