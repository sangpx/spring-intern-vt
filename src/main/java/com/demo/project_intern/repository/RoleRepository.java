package com.demo.project_intern.repository;

import com.demo.project_intern.dto.RoleDto;
import com.demo.project_intern.dto.request.role.RoleSearchRequest;
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
    @Query(value =
            "SELECT new com.demo.project_intern.dto.RoleDto(r.code, r.name) " +
                    "FROM RoleEntity r " +
                    "WHERE (:#{#request.code} IS NULL OR :#{#request.code} = '' OR r.code LIKE %:#{#request.code}%) " +
                    "AND (:#{#request.name} IS NULL OR :#{#request.name} = '' OR r.name LIKE %:#{#request.name}%) ",
            countQuery =
                    "SELECT COUNT(r) FROM RoleEntity r " +
                            "WHERE (:#{#request.code} IS NULL OR :#{#request.code} = '' OR r.code LIKE %:#{#request.code}%) " +
                            "AND (:#{#request.name} IS NULL OR :#{#request.name} = '' OR r.name LIKE %:#{#request.name}%) ")
    Page<RoleDto> search(@Param("request") RoleSearchRequest request, Pageable pageable);
}
