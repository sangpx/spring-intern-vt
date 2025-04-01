package com.demo.project_intern.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "post_like")
public class PostLikeEntity extends AbstractEntity<Long>  {

    @Column(name = "like_at")
    private LocalDate likeAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;
}
