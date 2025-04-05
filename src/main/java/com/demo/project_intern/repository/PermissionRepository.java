package com.demo.project_intern.repository;

import com.demo.project_intern.dto.PermissionDto;
import com.demo.project_intern.dto.request.permission.PermissionSearchRequest;
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
    List<PermissionEntity> findByCodeIn(Set<String> permissionCodes);

    @Query(value =
            "SELECT new com.demo.project_intern.dto.PermissionDto(p.code, p.name) " +
                    "FROM PermissionEntity p " +
                    "WHERE (:#{#request.code} IS NULL OR :#{#request.code} = '' OR p.code LIKE %:#{#request.code}%) " +
                    "AND (:#{#request.name} IS NULL OR :#{#request.name} = '' OR p.name LIKE %:#{#request.name}%) ",
            countQuery =
                    "SELECT COUNT(p) FROM PermissionEntity p " +
                            "WHERE (:#{#request.code} IS NULL OR :#{#request.code} = '' OR p.code LIKE %:#{#request.code}%) " +
                            "AND (:#{#request.name} IS NULL OR :#{#request.name} = '' OR p.name LIKE %:#{#request.name}%) ")
    Page<PermissionDto> search(@Param("request") PermissionSearchRequest request, Pageable pageable);
}
