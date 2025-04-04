package com.demo.project_intern.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    private String code;
    private String title;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String description;
    private String author;
    private String publisher;
    private Integer publishedYear;

    public BookDto(String code, String title, Integer publishedYear, String author, String publisher) {
        this.code = code;
        this.title = title;
        this.publishedYear = publishedYear;
        this.author = author;
        this.publisher = publisher;
    }
}