package com.demo.project_intern.dto.request.comment;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentUpdateRequest {
    @NotNull
    private String content;
    private Long postId;
}