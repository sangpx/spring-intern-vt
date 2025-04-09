package com.demo.project_intern.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopLikedPostDto {
    private String postTitle;
    private Long likeCount;
}