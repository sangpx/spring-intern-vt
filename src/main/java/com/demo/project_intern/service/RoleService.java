package com.demo.project_intern.service;

import com.demo.project_intern.dto.RoleDto;
import com.demo.project_intern.dto.request.role.RoleCreateRequest;
import com.demo.project_intern.dto.request.role.RoleUpdateRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
    RoleDto createRole(RoleCreateRequest request);
    List<RoleDto> getRoles();
    RoleDto getRole(Long roleId);
    RoleDto updateRole(Long roleId, RoleUpdateRequest request);
    void deleteRole(Long roleId);
    Page<RoleDto> searchRoles (String keyword, String code, int page, int size, String sortBy, String direction);
}
