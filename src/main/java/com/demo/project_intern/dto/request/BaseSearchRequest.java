package com.demo.project_intern.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseSearchRequest {
    private int page = 0;
    private int size = 10;
    private String sortBy;
    private String direction;
}