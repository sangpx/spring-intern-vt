package com.demo.project_intern.repository;

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

    @Query("SELECT bb FROM BorrowBookEntity bb WHERE " +
            "(:code IS NULL OR LOWER(bb.code) LIKE LOWER(CONCAT('%', :code, '%')))")
    Page<BorrowBookEntity> searchBorrowBooks (@Param("code") String code, Pageable pageable);
}
