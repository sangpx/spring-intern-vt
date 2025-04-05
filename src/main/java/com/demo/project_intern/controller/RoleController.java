package com.demo.project_intern.controller;

import com.demo.project_intern.config.Translator;
import com.demo.project_intern.constant.EntityType;
import com.demo.project_intern.dto.RoleDto;
import com.demo.project_intern.dto.request.role.RoleCreateRequest;
import com.demo.project_intern.dto.request.role.RoleSearchRequest;
import com.demo.project_intern.dto.request.role.RoleUpdateRequest;
import com.demo.project_intern.dto.response.ResponseData;
import com.demo.project_intern.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/role")
@Slf4j
@Tag(name = "Role Controller")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    @Operation(method = "POST", summary = "Create Role", description = "API Create New Role")
    public ResponseData<RoleDto> createRole(@RequestBody @Valid RoleCreateRequest request) {
        return ResponseData.<RoleDto>builder()
                .message(Translator.getSuccessMessage("add", EntityType.ROLE))
                .data(roleService.createRole(request))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    @Operation(method = "GET", summary = "Get List Roles", description = "API Get List Roles")
    public ResponseData<List<RoleDto>> getRoles() {
        return ResponseData.<List<RoleDto>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.ROLE))
                .data(roleService.getRoles())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{roleId}")
    @Operation(method = "GET", summary = "Get Detail Role", description = "API Get Detail Role")
    public ResponseData<RoleDto> getRole(@PathVariable("roleId") Long roleId) {
        return ResponseData.<RoleDto>builder()
                .message(Translator.getSuccessMessage("getDetail", EntityType.ROLE))
                .data(roleService.getRole(roleId))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{roleId}")
    @Operation(method = "PUT", summary = "Update Role", description = "API Update Role")
    public ResponseData<RoleDto> updateRole(@PathVariable("roleId") Long roleId, @RequestBody RoleUpdateRequest request) {
        return ResponseData.<RoleDto>builder()
                .message(Translator.getSuccessMessage("update", EntityType.ROLE))
                .data(roleService.updateRole(roleId, request))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{roleId}")
    @Operation(method = "DELETE", summary = "Delete Role", description = "API Delete Role")
    public String deleteRole(@PathVariable("roleId") Long roleId) {
        roleService.deleteRole(roleId);
        return "Deleted Successfully!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/paging")
    @Operation(method = "POST", summary = "Get Paging Roles", description = "API Get Paging Roles")
    public ResponseData<Page<RoleDto>> getPagingCategories(@RequestBody RoleSearchRequest request) {
        return ResponseData.<Page<RoleDto>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.ROLE))
                .data(roleService.search(request))
                .build();
    }
}
