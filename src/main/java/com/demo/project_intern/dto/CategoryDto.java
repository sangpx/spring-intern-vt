package com.demo.project_intern.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private Long id;
    private String code;
    private String name;
    private String description;
}
