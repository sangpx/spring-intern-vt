package com.demo.project_intern.service;

import com.demo.project_intern.dto.BorrowBookDto;
import com.demo.project_intern.dto.request.borrowBook.BorrowBookCreateRequest;
import com.demo.project_intern.dto.request.borrowBook.BorrowBookSearchRequest;
import com.demo.project_intern.dto.request.borrowBook.BorrowBookUpdateRequest;

import java.util.List;

public interface BorrowBookService extends GenericSearchService<BorrowBookSearchRequest, BorrowBookDto> {
    BorrowBookDto createBorrowBook(BorrowBookCreateRequest request);
    List<BorrowBookDto> getBorrowBooks();
    BorrowBookDto getBorrowBook(Long borrowBookId);
    BorrowBookDto updateBorrowBook(Long borrowBookId, BorrowBookUpdateRequest request);
    void deleteBorrowBook(Long borrowBookId);
}