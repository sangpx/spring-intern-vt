package com.demo.project_intern.repository;

import com.demo.project_intern.dto.BookDto;
import com.demo.project_intern.dto.request.book.BookSearchRequest;
import com.demo.project_intern.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Optional<BookEntity> findByCode(String code);

    boolean existsByCode(String code);

    @Query(value =
            "SELECT new com.demo.project_intern.dto.BookDto(b.code, b.title, b.publishedYear, b.author, b.publisher) " +
                    "FROM BookEntity b " +
                    "WHERE (:#{#request.code} IS NULL OR b.code LIKE %:#{#request.code}%) " +
                    "AND (:#{#request.title} IS NULL OR b.title LIKE %:#{#request.title}%) " +
                    "AND (:#{#request.publishedYear} IS NULL OR b.publishedYear = :#{#request.publishedYear}) " +
                    "AND (:#{#request.author} IS NULL OR b.author LIKE %:#{#request.author}%) " +
                    "AND (:#{#request.publisher} IS NULL OR b.publisher LIKE %:#{#request.publisher}%)",
            countQuery =
            "SELECT COUNT(b) FROM BookEntity b " +
                    "WHERE (:#{#request.code} IS NULL OR b.code LIKE %:#{#request.code}%) " +
                    "AND (:#{#request.title} IS NULL OR b.title LIKE %:#{#request.title}%) " +
                    "AND (:#{#request.publishedYear} IS NULL OR b.publishedYear = :#{#request.publishedYear}) " +
                    "AND (:#{#request.author} IS NULL OR b.author LIKE %:#{#request.author}%) " +
                    "AND (:#{#request.publisher} IS NULL OR b.publisher LIKE %:#{#request.publisher}%)")
    Page<BookDto> search(@Param("request") BookSearchRequest request, Pageable pageable);

}
