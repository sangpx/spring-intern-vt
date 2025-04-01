package com.demo.project_intern.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "post")
public class PostEntity extends AbstractEntity<Long>  {
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLikeEntity> postLikes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<CommentEntity> comments = new ArrayList<>();
}
