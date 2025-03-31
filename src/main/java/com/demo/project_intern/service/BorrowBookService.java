package com.demo.project_intern.service;

import com.demo.project_intern.dto.BorrowBookDto;
import com.demo.project_intern.dto.request.borrowBook.BorrowBookCreateRequest;
import com.demo.project_intern.dto.request.borrowBook.BorrowBookUpdateRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BorrowBookService {
    BorrowBookDto createBorrowBook(BorrowBookCreateRequest request);
    List<BorrowBookDto> getBorrowBooks();
    BorrowBookDto getBorrowBook(Long borrowBookId);
    BorrowBookDto updateBorrowBook(Long borrowBookId, BorrowBookUpdateRequest request);
    void deleteBorrowBook(Long borrowBookId);
    Page<BorrowBookDto> searchBorrowBooks (String code, int page, int size, String sortBy, String direction);
}
