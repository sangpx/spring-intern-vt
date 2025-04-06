package com.demo.project_intern.dto.response;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemoveRoleResponse {
   private Long userId;
   private List<String> removedRoles;
   private List<String> notAssignedRoles;
}