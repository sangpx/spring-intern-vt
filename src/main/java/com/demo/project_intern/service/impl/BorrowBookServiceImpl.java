package com.demo.project_intern.service.impl;

import com.demo.project_intern.constant.BorrowStatus;
import com.demo.project_intern.constant.ErrorCode;
import com.demo.project_intern.dto.BorrowBookDto;
import com.demo.project_intern.dto.BorrowDetailDto;
import com.demo.project_intern.dto.request.borrowBook.BorrowBookCreateRequest;
import com.demo.project_intern.dto.request.borrowBook.BorrowBookSearchRequest;
import com.demo.project_intern.dto.request.borrowBook.BorrowBookUpdateRequest;
import com.demo.project_intern.entity.BookEntity;
import com.demo.project_intern.entity.BorrowBookEntity;
import com.demo.project_intern.entity.BorrowDetailEntity;
import com.demo.project_intern.entity.UserEntity;
import com.demo.project_intern.exception.BaseLibraryException;
import com.demo.project_intern.repository.BookRepository;
import com.demo.project_intern.repository.BorrowBookRepository;
import com.demo.project_intern.repository.UserRepository;
import com.demo.project_intern.service.BorrowBookService;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


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
        //TODO: get info user


        //get list bookId from request
        List<Long> bookIdRequests = request.getBorrowDetails()
                        .stream()
                        .map(BorrowDetailDto::getBookId)
                        .toList();
        Map<Long, BookEntity> bookMap = bookRepository.findAllById(bookIdRequests)
                        .stream().collect(Collectors.toMap(BookEntity::getId, book -> book));
        //create borrowDetail
        List<BorrowDetailEntity> borrowDetailEntities = request.getBorrowDetails()
                .stream()
                .map(detailDto -> {
                    BorrowDetailEntity detailEntity = mapper.map(detailDto, BorrowDetailEntity.class);
                    detailEntity.setBorrowBook(borrowBookEntity);
                    detailEntity.setQuantity(detailDto.getQuantity());
                    //TODO: set bookId
                    detailEntity.setBook(bookMap.get(detailDto.getBookId()));
                    return detailEntity;
                })
                .collect(Collectors.toList());
        //create borrowBook
        borrowBookEntity.setCode(UUID.randomUUID().toString());
        borrowBookEntity.setBorrowStatus(BorrowStatus.BORROWED);
        borrowBookEntity.setBorrowDetails(borrowDetailEntities);

        //set fake user
        UserEntity user = new UserEntity();
        user.setId(1L);
        borrowBookEntity.setUser(user);

        borrowBookRepository.save(borrowBookEntity);
        BorrowBookDto borrowBookDto = mapper.map(borrowBookEntity, BorrowBookDto.class);
        borrowBookDto.setUserId(user.getId());
        return borrowBookDto;
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
    public Page<BorrowBookDto> search(BorrowBookSearchRequest request) {
        Pageable pageable = PageableUtils.from(request);
        return  borrowBookRepository.search(request, pageable);
    }
}
