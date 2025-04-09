package com.demo.project_intern.dto;

import com.demo.project_intern.dto.response.CommentReplyResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private String content;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long postId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long parentCommentId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long userId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CommentReplyResponse> replies;

    public CommentDto(String content) {
        this.content = content;
    }
}
