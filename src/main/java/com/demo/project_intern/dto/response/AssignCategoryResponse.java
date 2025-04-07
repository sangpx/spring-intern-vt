package com.demo.project_intern.dto.response;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignCategoryResponse {
   private Long bookId;
   private List<String> addedCategories;
   private List<String> duplicateCategories;
}