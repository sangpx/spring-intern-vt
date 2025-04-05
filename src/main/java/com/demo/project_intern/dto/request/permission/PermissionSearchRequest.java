package com.demo.project_intern.dto.request.permission;

import com.demo.project_intern.dto.request.BaseSearchRequest;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionSearchRequest extends BaseSearchRequest {
    private String code;
    private String name;
}