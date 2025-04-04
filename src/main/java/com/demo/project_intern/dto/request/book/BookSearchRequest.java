package com.demo.project_intern.dto.request.book;

import com.demo.project_intern.dto.request.BaseSearchRequest;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookSearchRequest extends BaseSearchRequest {
    private String code;
    private String title;
    private String author;
    private String publisher;
    private Integer publishedYear;
}