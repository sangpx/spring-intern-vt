package com.demo.project_intern.service;

import com.demo.project_intern.dto.PermissionDto;
import com.demo.project_intern.dto.request.permission.PermissionCreateRequest;
import com.demo.project_intern.dto.request.permission.PermissionSearchRequest;
import com.demo.project_intern.dto.request.permission.PermissionUpdateRequest;

import java.util.List;

public interface PermissionService extends GenericSearchService<PermissionSearchRequest, PermissionDto> {
    PermissionDto createPermission(PermissionCreateRequest request);
    List<PermissionDto> getPermissions();
    PermissionDto getPermission(Long permissionId);
    PermissionDto updatePermission(Long permissionId, PermissionUpdateRequest request);
    void deletePermission(Long permissionId);
}
