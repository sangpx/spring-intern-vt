package com.demo.project_intern.repository;

import com.demo.project_intern.dto.CategoryDto;
import com.demo.project_intern.dto.request.category.CategorySearchRequest;
import com.demo.project_intern.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByCode(String code);
    List<CategoryEntity> findByCodeIn(Set<String> codes);

    @Query(value =
            "SELECT new com.demo.project_intern.dto.CategoryDto(c.code, c.name, c.description) " +
                    "FROM CategoryEntity c " +
                    "WHERE (:#{#request.code} IS NULL OR :#{#request.code} = '' OR c.code LIKE %:#{#request.code}%) " +
                    "AND (:#{#request.name} IS NULL OR :#{#request.name} = '' OR c.name LIKE %:#{#request.name}%) " +
                    "AND (:#{#request.description} IS NULL OR :#{#request.description} = '' OR c.description LIKE %:#{#request.description}%) ",
            countQuery =
                    "SELECT COUNT(c) FROM CategoryEntity c " +
                            "WHERE (:#{#request.code} IS NULL OR :#{#request.code} = '' OR c.code LIKE %:#{#request.code}%) " +
                            "AND (:#{#request.name} IS NULL OR :#{#request.name} = '' OR c.name LIKE %:#{#request.name}%) " +
                            "AND (:#{#request.description} IS NULL OR :#{#request.description} = '' OR c.description LIKE %:#{#request.description}%) ")
    Page<CategoryDto> search(@Param("request") CategorySearchRequest request, Pageable pageable);
}
