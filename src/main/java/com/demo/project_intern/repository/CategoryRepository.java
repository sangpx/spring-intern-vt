package com.demo.project_intern.repository;

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
    @Query("SELECT c FROM CategoryEntity c WHERE " +
            "(:keyword IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:code IS NULL OR LOWER(c.code) LIKE LOWER(CONCAT('%', :code, '%')))")
    Page<CategoryEntity> searchCategories (@Param("keyword") String keyword,
                                           @Param("code") String code,
                                           Pageable pageable);
    List<CategoryEntity> findByCodeIn(Set<String> codes);

}
