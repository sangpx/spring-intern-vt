package com.demo.project_intern.dto.request.borrowBook;

import com.demo.project_intern.dto.BorrowDetailDto;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowBookCreateRequest {
    private String code;
    private LocalDate borrowDate;
    private LocalDate expectedReturnDate;
    private Long userId;
    private Set<BorrowDetailDto> borrowDetails;
}
