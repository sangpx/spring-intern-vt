package com.demo.project_intern.dto.request.borrowBook;

import com.demo.project_intern.dto.BorrowDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowBookUpdateRequest {
    private Set<BorrowDetailDto> borrowDetails;
}
