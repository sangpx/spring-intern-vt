package com.demo.project_intern.repository;

import com.demo.project_intern.dto.CommentDto;
import com.demo.project_intern.dto.request.comment.CommentSearchRequest;
import com.demo.project_intern.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query(value =
            "SELECT new com.demo.project_intern.dto.CommentDto(c.content) " +
                    "FROM CommentEntity c " +
                    "WHERE (:#{#request.content} IS NULL OR :#{#request.content} = '' OR c.content LIKE %:#{#request.content}%) ",
            countQuery =
                    "SELECT COUNT(c) FROM CommentEntity c " +
                    "WHERE (:#{#request.content} IS NULL OR :#{#request.content} = '' OR c.content LIKE %:#{#request.content}%) " )
    Page<CommentDto> search(@Param("request") CommentSearchRequest request, Pageable pageable);

    List<CommentEntity> findByParentCommentId(Long parentCommentId);
}
