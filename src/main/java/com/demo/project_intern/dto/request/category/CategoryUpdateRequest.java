package com.demo.project_intern.dto.request.category;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryUpdateRequest {
    private String name;
    private String description;
}
