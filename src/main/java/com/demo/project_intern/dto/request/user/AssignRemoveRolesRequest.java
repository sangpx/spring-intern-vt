package com.demo.project_intern.dto.request.user;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignRemoveRolesRequest {
    private Long userId;
    private List<Long> roleIds;
}
