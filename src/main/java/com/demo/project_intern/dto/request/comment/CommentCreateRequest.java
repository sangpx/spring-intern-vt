package com.demo.project_intern.dto.request.comment;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentCreateRequest {
    @NotNull
    private String content;
    private Long postId;
    private Long parentCommentId;
}
