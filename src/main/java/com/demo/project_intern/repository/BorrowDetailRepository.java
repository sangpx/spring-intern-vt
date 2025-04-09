package com.demo.project_intern.repository;

import com.demo.project_intern.constant.BorrowStatus;
import com.demo.project_intern.entity.BorrowBookEntity;
import com.demo.project_intern.entity.BorrowDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowDetailRepository extends JpaRepository<BorrowDetailEntity, Long> {
    List<BorrowDetailEntity> findByBorrowBook(BorrowBookEntity borrowBook);
    @Query("SELECT SUM(bd.quantity) FROM BorrowDetailEntity bd " +
            "WHERE bd.book.id = :bookId " +
            "AND bd.actualReturnDate IS NULL")
    Integer sumQuantityByBookIdAndReturnedFalse(@Param("bookId") Long bookId);

    @Query("SELECT COALESCE(SUM(d.quantity), 0) " +
    "FROM BorrowDetailEntity d " +
    "JOIN d.borrowBook b " +
    "WHERE d.book.id = :bookId " +
    "AND b.borrowStatus = :status ")
    int sumBorrowedQuantity(@Param("bookId") Long bookId, @Param("status") BorrowStatus status);
}
