package com.demo.project_intern.dto.response;

import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignCategoryResponse extends BaseAssignResponse {
   private Long bookId;
}