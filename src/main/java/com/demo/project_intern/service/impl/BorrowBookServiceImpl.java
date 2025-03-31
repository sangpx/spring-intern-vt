package com.demo.project_intern.service.impl;

import com.demo.project_intern.constant.BorrowStatus;
import com.demo.project_intern.constant.ErrorCode;
import com.demo.project_intern.dto.BorrowBookDto;
import com.demo.project_intern.dto.BorrowDetailDto;
import com.demo.project_intern.dto.request.borrowBook.BorrowBookCreateRequest;
import com.demo.project_intern.dto.request.borrowBook.BorrowBookUpdateRequest;
import com.demo.project_intern.entity.BookEntity;
import com.demo.project_intern.entity.BorrowBookEntity;
import com.demo.project_intern.exception.BaseLibraryException;
import com.demo.project_intern.repository.BookRepository;
import com.demo.project_intern.repository.BorrowBookRepository;
import com.demo.project_intern.repository.UserRepository;
import com.demo.project_intern.service.BorrowBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class BorrowBookServiceImpl implements BorrowBookService {

    private final BorrowBookRepository borrowBookRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;


    @Override
    public BorrowBookDto createBorrowBook(BorrowBookCreateRequest request) {
        BorrowBookEntity borrowBookEntity = mapper.map(request, BorrowBookEntity.class);
        //TODO: lay ra thong tin nguoi dung

        //tim xem da ton tai code phieu muon hay chua
        if(borrowBookRepository.existsByCode(request.getCode())) {
            throw new BaseLibraryException(ErrorCode.BORROW_BOOK_EXISTED);
        }

        //TODO



        borrowBookEntity.setBorrowStatus(BorrowStatus.BORROWED);
        borrowBookRepository.save(borrowBookEntity);
        return mapper.map(borrowBookEntity, BorrowBookDto.class);
    }

    @Override
    public List<BorrowBookDto> getBorrowBooks() {
        return borrowBookRepository.findAll()
                .stream()
                .map(BorrowBookEntity -> mapper.map(BorrowBookEntity, BorrowBookDto.class))
                .toList();
    }

    @Override
    public BorrowBookDto getBorrowBook(Long borrowBookId) {
        BorrowBookEntity borrowBookEntity = borrowBookRepository.findById(borrowBookId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        return mapper.map(borrowBookEntity, BorrowBookDto.class);
    }

    @Override
    public BorrowBookDto updateBorrowBook(Long borrowBookId, BorrowBookUpdateRequest request) {
        BorrowBookEntity borrowBook = borrowBookRepository.findById(borrowBookId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        borrowBook.setUpdatedAt(LocalDate.now());
        borrowBookRepository.save(borrowBook);
        return mapper.map(borrowBook, BorrowBookDto.class);
    }

    @Override
    public void deleteBorrowBook(Long borrowBookId) {
        BorrowBookEntity borrowBook = borrowBookRepository.findById(borrowBookId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        borrowBookRepository.delete(borrowBook);
    }

    @Override
    public Page<BorrowBookDto> searchBorrowBooks(String code, int page, int size, String sortBy, String direction) {
        Sort sort = "desc".equalsIgnoreCase(direction)
                            ? Sort.by(sortBy).descending()
                            : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<BorrowBookEntity> pageBorrowBooks = borrowBookRepository.searchBorrowBooks(code, pageable);
        return pageBorrowBooks.map(pageBorrowBook -> mapper.map(pageBorrowBook, BorrowBookDto.class));
    }
}
