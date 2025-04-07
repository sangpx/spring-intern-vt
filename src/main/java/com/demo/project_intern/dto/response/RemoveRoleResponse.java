package com.demo.project_intern.dto.response;

import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemoveRoleResponse extends BaseRemoveResponse {
   private Long userId;
}