package com.demo.project_intern.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto {
    private Long id;
    private String code;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<PermissionDto> permissions;
}
