package com.demo.project_intern.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private String title;
    private String content;
    private Long bookId;
    private Long userId;
}
