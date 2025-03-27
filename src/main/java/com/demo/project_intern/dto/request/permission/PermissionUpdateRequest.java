package com.demo.project_intern.dto.request.permission;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionUpdateRequest {
    @NotNull
    private String name;
}
