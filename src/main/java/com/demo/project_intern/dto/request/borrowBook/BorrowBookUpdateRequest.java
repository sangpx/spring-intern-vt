package com.demo.project_intern.dto.request.borrowBook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowBookUpdateRequest {
    private LocalDate borrowDate;
    private LocalDate expectedReturnDate;
    private Long userId;
}
