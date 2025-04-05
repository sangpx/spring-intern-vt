package com.demo.project_intern.repository;

import com.demo.project_intern.dto.BorrowBookDto;
import com.demo.project_intern.dto.request.borrowBook.BorrowBookSearchRequest;
import com.demo.project_intern.entity.BorrowBookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BorrowBookRepository extends JpaRepository<BorrowBookEntity, Long> {
    boolean existsByCode(String code);
    Optional<BorrowBookEntity> findByCode(String code);

    @Query(
            value =
    "SELECT new com.demo.project_intern.dto.BorrowBookDto(bb.borrowDate, bb.expectedReturnDate, bb.code) " +
    "FROM BorrowBookEntity bb " +
    "WHERE (:#{#request.code} IS NULL OR :#{#request.code} = '' OR bb.code LIKE %:#{#request.code}%) " +
    "AND (:#{#request.borrowDateFrom} IS NULL OR bb.borrowDate >= :#{#request.borrowDateFrom}) " +
    "AND (:#{#request.borrowDateTo} IS NULL OR bb.borrowDate <= :#{#request.borrowDateTo}) " +
    "AND (:#{#request.expectedReturnDateFrom} IS NULL OR bb.expectedReturnDate >= :#{#request.expectedReturnDateFrom}) " +
    "AND (:#{#request.expectedReturnDateTo} IS NULL OR bb.expectedReturnDate <= :#{#request.expectedReturnDateTo}) ",
            countQuery =
    "SELECT COUNT(bb) FROM BorrowBookEntity bb " +
    "WHERE (:#{#request.code} IS NULL OR :#{#request.code} = '' OR bb.code LIKE %:#{#request.code}%) " +
    "AND (:#{#request.borrowDateFrom} IS NULL OR bb.borrowDate >= :#{#request.borrowDateFrom}) " +
    "AND (:#{#request.borrowDateTo} IS NULL OR bb.borrowDate <= :#{#request.borrowDateTo}) " +
    "AND (:#{#request.expectedReturnDateFrom} IS NULL OR bb.expectedReturnDate >= :#{#request.expectedReturnDateFrom}) " +
    "AND (:#{#request.expectedReturnDateTo} IS NULL OR bb.expectedReturnDate <= :#{#request.expectedReturnDateTo}) ")
    Page<BorrowBookDto> search(@Param("request") BorrowBookSearchRequest request, Pageable pageable);
}
