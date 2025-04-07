package com.demo.project_intern.dto.response;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryProcessResult {
   private List<String> processedCategories;
   private List<String> skippedCategories;
}