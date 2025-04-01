package com.demo.project_intern.dto;

import lombok.*;
import org.springframework.data.domain.Pageable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchKeywordQuery {
    private String keyword;
    private String code;
    Pageable pageable;
}
