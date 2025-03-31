package com.demo.project_intern.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowBookDto {
    private LocalDate borrowDate;
    private LocalDate expectedReturnDate;
    private Long userId;
    private Set<BorrowDetailDto> borrowDetails;
}
