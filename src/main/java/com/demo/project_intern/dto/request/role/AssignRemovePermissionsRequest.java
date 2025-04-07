package com.demo.project_intern.dto.request.role;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignRemovePermissionsRequest {
    private Long roleId;
    private List<Long> permissionIds;
}