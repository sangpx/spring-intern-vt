package com.demo.project_intern.dto.request.role;

import com.demo.project_intern.dto.PermissionDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleCreateRequest {
    @NotNull
    private String code;
    private String name;
    private Set<PermissionDto> permissions;
}
