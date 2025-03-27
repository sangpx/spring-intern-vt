package com.demo.project_intern.repository;

import com.demo.project_intern.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    boolean existsByCode(String code);
    @Query("SELECT r FROM RoleEntity r WHERE " +
            "(:keyword IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:code IS NULL OR LOWER(r.code) LIKE LOWER(CONCAT('%', :code, '%')))")
    Page<RoleEntity> searchRoles (@Param("keyword") String keyword,
                                  @Param("code") String code,
                                  Pageable pageable);
}
