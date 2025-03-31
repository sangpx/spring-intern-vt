package com.demo.project_intern.repository;

import com.demo.project_intern.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Optional<BookEntity> findByCode(String code);

    boolean existsByCode(String code);
    @Query("SELECT b FROM BookEntity b WHERE " +
            "(:keyword IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:code IS NULL OR LOWER(b.code) LIKE LOWER(CONCAT('%', :code, '%')))")
    Page<BookEntity> searchBooks (@Param("keyword") String keyword,
                                  @Param("code") String code,
                                  Pageable pageable);
}
