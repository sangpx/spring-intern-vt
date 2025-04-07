package com.demo.project_intern.dto.response;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemoveCategoryResponse extends BaseRemoveResponse {
   private Long bookId;
}