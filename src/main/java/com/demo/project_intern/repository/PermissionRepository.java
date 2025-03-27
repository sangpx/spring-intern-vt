package com.demo.project_intern.repository;

import com.demo.project_intern.entity.PermissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    boolean existsByCode(String code);
    @Query("SELECT p FROM PermissionEntity p WHERE " +
            "(:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:code IS NULL OR LOWER(p.code) LIKE LOWER(CONCAT('%', :code, '%')))")
    Page<PermissionEntity> searchPermissions (@Param("keyword") String keyword,
                                              @Param("code") String code,
                                              Pageable pageable);

    List<PermissionEntity> findByCodeIn(Set<String> permissionCodes);
}
