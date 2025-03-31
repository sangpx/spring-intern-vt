package com.demo.project_intern.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowDetailDto {
    private LocalDate actualReturnDate;
    private int quantity;
    private String status;
    private Long bookId;
    private Long borrowBookId;
}
