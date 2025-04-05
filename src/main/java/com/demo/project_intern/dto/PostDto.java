package com.demo.project_intern.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private String title;
    private String content;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long bookId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long userId;

    public PostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
