package com.demo.project_intern.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    private Long id;
    private String code;
    private String title;
    private String description;
    private String author;
    private String publisher;
    private LocalDate publishedYear;

    public BookDto(Long id, String code, String title, String description, String author, String publisher) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
    }
}
