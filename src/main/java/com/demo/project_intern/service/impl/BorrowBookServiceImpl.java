package com.demo.project_intern.service.impl;

import com.demo.project_intern.constant.BorrowStatus;
import com.demo.project_intern.constant.ErrorCode;
import com.demo.project_intern.dto.BorrowBookDto;
import com.demo.project_intern.dto.BorrowDetailDto;
import com.demo.project_intern.dto.request.borrowBook.BorrowBookCreateRequest;
import com.demo.project_intern.dto.request.borrowBook.BorrowBookSearchRequest;
import com.demo.project_intern.dto.request.borrowBook.BorrowBookUpdateRequest;
import com.demo.project_intern.entity.*;
import com.demo.project_intern.exception.BaseLibraryException;
import com.demo.project_intern.repository.BookRepository;
import com.demo.project_intern.repository.BorrowBookRepository;
import com.demo.project_intern.repository.BorrowDetailRepository;
import com.demo.project_intern.repository.UserRepository;
import com.demo.project_intern.service.BorrowBookService;
import com.demo.project_intern.utils.CommonUtil;
import com.demo.project_intern.utils.PageableUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class BorrowBookServiceImpl implements BorrowBookService {

    private final BorrowBookRepository borrowBookRepository;
    private final BorrowDetailRepository borrowDetailRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final JavaMailSender mailSender;
    private final CommonUtil commonUtil;



    @Override
    @Transactional
    public BorrowBookDto createBorrowBook(BorrowBookCreateRequest request) {

        //get info user from token
        UserEntity currentUser = commonUtil.getCurrentUser();

        // Validate dates
        validateBorrowDates(LocalDate.now(), request.getExpectedReturnDate());

        // create BorrowBookEntity entity
        BorrowBookEntity borrowBook = BorrowBookEntity
                .builder()
                .code(UUID.randomUUID().toString())
                .borrowDate(LocalDate.now())
                .expectedReturnDate(request.getExpectedReturnDate())
                .borrowStatus(BorrowStatus.BORROWED)
                .user(currentUser)
                .build();

        List<BorrowDetailEntity> borrowDetails = new ArrayList<>();

        for (BorrowDetailDto detailDto : request.getBorrowDetails()) {
            if (detailDto.getQuantity() <= 0) {
                throw new BaseLibraryException(ErrorCode.QUANTITY_VALID);
            }
            BookEntity book = bookRepository.findById(detailDto.getBookId())
                                    .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
            if (book.getAvailableQuantity() < detailDto.getQuantity()) {
                throw new BaseLibraryException(ErrorCode.NOT_ENOUGH_BOOK_QUANTITY);
            }
            //set available of quantity for BookEntity
            book.setAvailableQuantity(book.getAvailableQuantity() - detailDto.getQuantity());

            // create BorrowDetailEntity entity
            BorrowDetailEntity detailEntity = BorrowDetailEntity
                    .builder()
                    .borrowBook(borrowBook)
                    .book(book)
                    .quantity(detailDto.getQuantity())
                    .returned(false)
                    .actualReturnDate(detailDto.getActualReturnDate())
                    .build();
            //add detailEntity -> borrowDetails
            borrowDetails.add(detailEntity);
        }

        //set BorrowDetails of borrowBook
        borrowBook.setBorrowDetails(borrowDetails);

        // save to database
        borrowBookRepository.save(borrowBook);
        bookRepository.saveAll(borrowDetails
                        .stream()
                        .map(BorrowDetailEntity::getBook)
                        .distinct()
                        .toList()
        );

        // Mapping -> DTO
        Set<BorrowDetailDto> detailDtos = borrowDetails
                .stream()
                .map(detail -> BorrowDetailDto
                                                    .builder()
                                                    .actualReturnDate(detail.getActualReturnDate())
                                                    .quantity(detail.getQuantity())
                                                    .bookId(detail.getBook().getId())
                                                    .build())
                .collect(Collectors.toSet());

        return BorrowBookDto
                .builder()
                .code(borrowBook.getCode())
                .borrowDate(borrowBook.getBorrowDate())
                .expectedReturnDate(borrowBook.getExpectedReturnDate())
                .borrowStatus(borrowBook.getBorrowStatus())
                .userId(currentUser.getId())
                .borrowDetails(detailDtos)
                .build();
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

        //lấy thông tin người dùng từ token
        UserEntity currentUser = commonUtil.getCurrentUser();

        // lấy thông tin BorrowBookEntity theo id
        BorrowBookEntity borrowBook = borrowBookRepository.findById(borrowBookId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));

        borrowBook.setUpdatedAt(LocalDate.now());

        for (BorrowDetailDto detailDto : request.getBorrowDetails()) {
            BookEntity book = bookRepository.findById(detailDto.getBookId())
                    .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));

            // Kiểm tra số lượng có đủ không
            if (book.getAvailableQuantity() < detailDto.getQuantity()) {
                throw new BaseLibraryException(ErrorCode.NOT_ENOUGH_BOOK_QUANTITY);
            }

            // Tìm xem đã có sách này trong ds chưa và chưa trả
            Optional<BorrowDetailEntity> existingDetailOpt = borrowBook.getBorrowDetails()
                    .stream()
                    .filter(d -> d.getBook().getId().equals(detailDto.getBookId()) && !d.isReturned())
                    .findFirst();

            if (existingDetailOpt.isPresent()) {
                // Đã tồn tại => cập nhật số lượng
                BorrowDetailEntity existingDetail = existingDetailOpt.get();
                existingDetail.setQuantity(existingDetail.getQuantity() + detailDto.getQuantity());

            } else {
                // Chưa tồn tại => tạo mới bản ghi
                BorrowDetailEntity newDetail = BorrowDetailEntity.builder()
                        .book(book)
                        .borrowBook(borrowBook)
                        .actualReturnDate(detailDto.getActualReturnDate())
                        .quantity(detailDto.getQuantity())
                        .returned(false)
                        .build();
                borrowBook.getBorrowDetails().add(newDetail);
            }

            // Cập nhật lại số lượng sách còn lại
            book.setAvailableQuantity(book.getAvailableQuantity() - detailDto.getQuantity());
            bookRepository.save(book);
        }
        borrowBookRepository.save(borrowBook);
        // Mapping -> DTO
        Set<BorrowDetailDto> detailDtos = borrowBook.getBorrowDetails()
                .stream()
                .map(detail -> BorrowDetailDto
                        .builder()
                        .actualReturnDate(detail.getActualReturnDate())
                        .quantity(detail.getQuantity())
                        .bookId(detail.getBook().getId())
                        .build())
                .collect(Collectors.toSet());
        return BorrowBookDto
                .builder()
                .code(borrowBook.getCode())
                .borrowDate(borrowBook.getBorrowDate())
                .expectedReturnDate(borrowBook.getExpectedReturnDate())
                .borrowStatus(borrowBook.getBorrowStatus())
                .userId(currentUser.getId())
                .borrowDetails(detailDtos)
                .build();
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


    private void validateBorrowDates(LocalDate borrowDate, LocalDate expectedReturnDate) {
        if (expectedReturnDate.isBefore(borrowDate)) {
            throw new BaseLibraryException(ErrorCode.INVALID_EXPECTED_RETURN_DATE);
        }
    }
}
