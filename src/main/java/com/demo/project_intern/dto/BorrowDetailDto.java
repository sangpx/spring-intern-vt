package com.demo.project_intern.dto;

import jakarta.validation.constraints.Min;
import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowDetailDto {
    @Min(1)
    private int quantity;
    private Long bookId;
}
