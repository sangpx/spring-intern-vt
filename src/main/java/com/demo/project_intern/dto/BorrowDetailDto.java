package com.demo.project_intern.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowDetailDto {
    private LocalDate actualReturnDate;
    //TODO: validation quantity
    private int quantity;
    private Long bookId;
}
