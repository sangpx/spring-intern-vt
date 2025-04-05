package com.demo.project_intern.dto.request.borrowBook;

import com.demo.project_intern.dto.request.BaseSearchRequest;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowBookSearchRequest extends BaseSearchRequest {
    private String code;
    private Long userId;
    private LocalDate borrowDateFrom;
    private LocalDate borrowDateTo;
    private LocalDate expectedReturnDateFrom;
    private LocalDate expectedReturnDateTo;
}