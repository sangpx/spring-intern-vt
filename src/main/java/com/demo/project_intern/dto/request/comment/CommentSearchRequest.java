package com.demo.project_intern.dto.request.comment;

import com.demo.project_intern.dto.request.BaseSearchRequest;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentSearchRequest extends BaseSearchRequest {
    private String content;
}