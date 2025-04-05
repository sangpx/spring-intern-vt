package com.demo.project_intern.dto.request.role;

import com.demo.project_intern.dto.request.BaseSearchRequest;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleSearchRequest extends BaseSearchRequest {
    private String code;
    private String name;
}