package com.demo.project_intern.dto.response;

import com.demo.project_intern.entity.BookEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportErrorDetail {
   private int rowNumber;
   private String errorMessage;
   private BookEntity bookData;
}