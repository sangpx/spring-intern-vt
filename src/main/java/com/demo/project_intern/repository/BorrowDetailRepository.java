package com.demo.project_intern.repository;

import com.demo.project_intern.entity.BorrowBookEntity;
import com.demo.project_intern.entity.BorrowDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowDetailRepository extends JpaRepository<BorrowDetailEntity, Long> {
    List<BorrowDetailEntity> findByBorrowBook(BorrowBookEntity borrowBook);
}
