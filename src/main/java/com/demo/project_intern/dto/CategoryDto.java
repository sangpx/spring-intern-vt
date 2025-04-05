package com.demo.project_intern.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private String code;
    private String name;
    private String description;

    public CategoryDto(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }
}
