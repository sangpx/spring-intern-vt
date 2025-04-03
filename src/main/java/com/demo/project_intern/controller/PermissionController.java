package com.demo.project_intern.controller;

import com.demo.project_intern.config.Translator;
import com.demo.project_intern.constant.EntityType;
import com.demo.project_intern.dto.PermissionDto;
import com.demo.project_intern.dto.request.permission.PermissionCreateRequest;
import com.demo.project_intern.dto.request.permission.PermissionUpdateRequest;
import com.demo.project_intern.dto.response.ResponseData;
import com.demo.project_intern.service.PermissionService;
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
@RequestMapping("${api.base-path}/permission")
@Slf4j
@Tag(name = "Permission Controller")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    @Operation(method = "POST", summary = "Create Permission", description = "API Create New Permission")
    public ResponseData<PermissionDto> createPermission(@RequestBody @Valid PermissionCreateRequest request) {
        return ResponseData.<PermissionDto>builder()
                .message(Translator.getSuccessMessage("add", EntityType.PERMISSION))
                .data(permissionService.createPermission(request))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    @Operation(method = "GET", summary = "Get List Permissions", description = "API Get List Permissions")
    public ResponseData<List<PermissionDto>> getPermissions() {
        return ResponseData.<List<PermissionDto>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.PERMISSION))
                .data(permissionService.getPermissions())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{permissionId}")
    @Operation(method = "GET", summary = "Get Detail Permission", description = "API Get Detail Permission")
    public ResponseData<PermissionDto> getPermission(@PathVariable("permissionId") Long permissionId) {
        return ResponseData.<PermissionDto>builder()
                .message(Translator.getSuccessMessage("getDetail", EntityType.PERMISSION))
                .data(permissionService.getPermission(permissionId))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{permissionId}")
    @Operation(method = "PUT", summary = "Update Permission", description = "API Update Permission")
    public ResponseData<PermissionDto> updatePermission(@PathVariable("permissionId") Long permissionId, @RequestBody PermissionUpdateRequest request) {
        return ResponseData.<PermissionDto>builder()
                .message(Translator.getSuccessMessage("update", EntityType.PERMISSION))
                .data(permissionService.updatePermission(permissionId, request))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{permissionId}")
    @Operation(method = "DELETE", summary = "Delete Permission", description = "API Delete Permission")
    public String deletePermission(@PathVariable("permissionId") Long permissionId) {
        permissionService.deletePermission(permissionId);
        return "Deleted Successfully!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/paging")
    @Operation(method = "GET", summary = "Get Paging Categories", description = "API Get Paging Categories")
    public ResponseData<Page<PermissionDto>> getPagingCategories(@RequestParam(required = false) String keyword,
                                                               @RequestParam(required = false) String code,
                                                               @RequestParam() int page,
                                                               @RequestParam() int size,
                                                               @RequestParam(defaultValue = "name") String sortBy,
                                                               @RequestParam(defaultValue = "asc") String direction) {
        return ResponseData.<Page<PermissionDto>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.PERMISSION))
                .data(permissionService.searchPermissions(keyword, code, page, size, sortBy, direction))
                .build();
    }
}
