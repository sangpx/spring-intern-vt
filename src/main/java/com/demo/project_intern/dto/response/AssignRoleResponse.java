package com.demo.project_intern.dto.response;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignRoleResponse {
   private Long userId;
   private List<String> addedRoles;
   private List<String> duplicateRoles;
}