package com.demo.project_intern.dto.request.post;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostLikeRequest {
    private Long postId;
}