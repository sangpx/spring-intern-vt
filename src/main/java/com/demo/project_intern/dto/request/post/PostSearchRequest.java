package com.demo.project_intern.dto.request.post;

import com.demo.project_intern.dto.request.BaseSearchRequest;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostSearchRequest extends BaseSearchRequest {
    private String title;
    private String content;
    private Long bookId;
    private Long userId;
}