package com.demo.project_intern.repository;

import com.demo.project_intern.dto.PostDto;
import com.demo.project_intern.dto.request.post.PostSearchRequest;
import com.demo.project_intern.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query(value =
            "SELECT new com.demo.project_intern.dto.PostDto(p.title, p.content) " +
                    "FROM PostEntity p " +
                    "WHERE (:#{#request.title} IS NULL OR :#{#request.title} = '' OR p.title LIKE %:#{#request.title}%) " +
                    "AND (:#{#request.content} IS NULL OR :#{#request.content} = '' OR p.content LIKE %:#{#request.content}%)",
            countQuery =
                    "SELECT COUNT(p) FROM PostEntity p " +
                            "WHERE (:#{#request.title} IS NULL OR :#{#request.title} = '' OR p.title LIKE %:#{#request.title}%) " +
                            "AND (:#{#request.content} IS NULL OR :#{#request.content} = '' OR p.content LIKE %:#{#request.content}%) ")
    Page<PostDto> search(@Param("request") PostSearchRequest request, Pageable pageable);
}