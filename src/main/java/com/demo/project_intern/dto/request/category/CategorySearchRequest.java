package com.demo.project_intern.dto.request.category;

import com.demo.project_intern.dto.request.BaseSearchRequest;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategorySearchRequest extends BaseSearchRequest {
    private String code;
    private String name;
    private String description;
}