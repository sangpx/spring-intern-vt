package com.demo.project_intern.service;

import com.demo.project_intern.dto.RoleDto;
import com.demo.project_intern.dto.request.role.AssignRemovePermissionsRequest;
import com.demo.project_intern.dto.request.role.RoleCreateRequest;
import com.demo.project_intern.dto.request.role.RoleSearchRequest;
import com.demo.project_intern.dto.request.role.RoleUpdateRequest;
import com.demo.project_intern.dto.request.user.AssignRemoveRolesRequest;
import com.demo.project_intern.dto.response.AssignPermissionResponse;
import com.demo.project_intern.dto.response.AssignRoleResponse;
import com.demo.project_intern.dto.response.RemovePermissionResponse;
import com.demo.project_intern.dto.response.RemoveRoleResponse;

import java.util.List;

public interface RoleService extends GenericSearchService<RoleSearchRequest, RoleDto> {
    RoleDto createRole(RoleCreateRequest request);
    List<RoleDto> getRoles();
    RoleDto getRole(Long roleId);
    RoleDto updateRole(Long roleId, RoleUpdateRequest request);
    void deleteRole(Long roleId);
    AssignPermissionResponse assignPermission(AssignRemovePermissionsRequest request);
    RemovePermissionResponse removePermission(AssignRemovePermissionsRequest request);
}