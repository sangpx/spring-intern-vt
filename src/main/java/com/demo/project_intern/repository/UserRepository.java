package com.demo.project_intern.repository;

import com.demo.project_intern.dto.BookDto;
import com.demo.project_intern.dto.UserDto;
import com.demo.project_intern.dto.request.book.BookSearchRequest;
import com.demo.project_intern.dto.request.user.UserSearchRequest;
import com.demo.project_intern.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    boolean existsByUserNameAndIdNot(String userName, Long userId);

    @Query(value =
        "SELECT new com.demo.project_intern.dto.UserDto(u.userName, u.fullName, u.email, u.phone, u.address) " +
        "FROM UserEntity u " +
        "WHERE (:#{#request.userName} IS NULL OR :#{#request.userName} = '' OR u.userName LIKE %:#{#request.userName}%) " +
        "AND (:#{#request.fullName} IS NULL OR :#{#request.fullName} = '' OR u.fullName LIKE %:#{#request.fullName}%) " +
        "AND (:#{#request.email} IS NULL OR :#{#request.email} = '' OR u.email LIKE %:#{#request.email}%) " +
        "AND (:#{#request.phone} IS NULL OR :#{#request.phone} = '' OR u.phone LIKE %:#{#request.phone}%) " +
        "AND (:#{#request.address} IS NULL OR :#{#request.address} = '' OR u.address LIKE %:#{#request.address}%)",
        countQuery =
        "SELECT COUNT(u) FROM UserEntity u " +
        "WHERE (:#{#request.userName} IS NULL OR :#{#request.userName} = '' OR u.userName LIKE %:#{#request.userName}%) " +
        "AND (:#{#request.fullName} IS NULL OR :#{#request.fullName} = '' OR u.fullName LIKE %:#{#request.fullName}%) " +
        "AND (:#{#request.email} IS NULL OR :#{#request.email} = '' OR u.email LIKE %:#{#request.email}%) " +
        "AND (:#{#request.phone} IS NULL OR :#{#request.phone} = '' OR u.phone LIKE %:#{#request.phone}%) " +
        "AND (:#{#request.address} IS NULL OR :#{#request.address} = '' OR u.address LIKE %:#{#request.address}%)")
    Page<UserDto> search(@Param("request") UserSearchRequest request, Pageable pageable);

}
