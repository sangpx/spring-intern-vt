package com.demo.project_intern.dto.request.book;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignRemoveCategoryRequest {
    private Long bookId;
    private List<Long> categoryIds;
}
