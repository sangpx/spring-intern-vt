package com.demo.project_intern.repository;

import com.demo.project_intern.entity.PostLikeEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {
    List<PostLikeEntity> findAllByPostId(Long postId);

    Optional<PostLikeEntity> findByUserIdAndPostId(Long userId, Long postId);

    long countByPostId(Long postId);

    @Query("SELECT p.post.id AS postId, COUNT(p.id) AS likeCount " +
            "FROM PostLikeEntity p " +
            "GROUP BY p.post.id " +
            "ORDER BY likeCount DESC")
    List<Object[]> findTop5MostLikedPosts(Pageable pageable);
}
