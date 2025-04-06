package com.demo.project_intern.dto.response;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleProcessResult {
   private List<String> processedRoles;
   private List<String> skippedRoles;
}