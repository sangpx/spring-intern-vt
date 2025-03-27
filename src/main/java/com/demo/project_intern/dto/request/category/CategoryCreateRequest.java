package com.demo.project_intern.dto.request.category;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryCreateRequest {
    @NotNull
    private String code;
    private String name;
    private String description;
}
