package com.demo.project_intern.service;

import com.demo.project_intern.dto.PermissionDto;
import com.demo.project_intern.dto.request.permission.PermissionCreateRequest;
import com.demo.project_intern.dto.request.permission.PermissionUpdateRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PermissionService {
    PermissionDto createPermission(PermissionCreateRequest request);
    List<PermissionDto> getPermissions();
    PermissionDto getPermission(Long permissionId);
    PermissionDto updatePermission(Long permissionId, PermissionUpdateRequest request);
    void deletePermission(Long permissionId);
    Page<PermissionDto> searchPermissions (String keyword, String code, int page, int size, String sortBy, String direction);
}
