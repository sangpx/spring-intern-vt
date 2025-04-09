package com.demo.project_intern.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryBookCountDto {
    private String categoryCode;
    private String categoryName;
    private Long bookCount;
}