package com.demo.project_intern.dto.request.role;

import com.demo.project_intern.dto.PermissionDto;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleUpdateRequest {
    private String name;
    private Set<PermissionDto> permissions;
}
