package com.demo.project_intern.dto.response;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemoveCategoryResponse {
   private Long bookId;
   private List<String> removedCategories;
   private List<String> notAssignedCategories;
}