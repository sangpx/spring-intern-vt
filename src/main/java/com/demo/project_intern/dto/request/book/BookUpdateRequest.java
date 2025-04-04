package com.demo.project_intern.dto.request.book;

import com.demo.project_intern.dto.CategoryDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookUpdateRequest {
    @NotNull
    private String title;
    private String description;
    private String author;
    private String publisher;
    private Integer publishedYear;
    private Set<CategoryDto> categories;
}
