package com.demo.project_intern.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long userId;
    private String code;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<BorrowDetailDto> borrowDetails;

    public BorrowBookDto(LocalDate borrowDate, LocalDate expectedReturnDate, String code) {
        this.borrowDate = borrowDate;
        this.expectedReturnDate = expectedReturnDate;
        this.code = code;
    }
}
