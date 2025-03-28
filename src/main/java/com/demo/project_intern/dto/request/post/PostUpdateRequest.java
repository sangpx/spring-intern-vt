package com.demo.project_intern.dto.request.post;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostUpdateRequest {
    @NotNull
    private String title;
    private String content;
}
