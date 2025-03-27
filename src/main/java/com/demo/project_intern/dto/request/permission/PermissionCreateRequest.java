package com.demo.project_intern.dto.request.permission;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionCreateRequest {
    @NotNull
    private String code;
    private String name;
}
