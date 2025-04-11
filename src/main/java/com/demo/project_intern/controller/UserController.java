package com.demo.project_intern.controller;

import com.demo.project_intern.config.Translator;
import com.demo.project_intern.constant.EntityType;
import com.demo.project_intern.dto.request.user.AssignRemoveRolesRequest;
import com.demo.project_intern.dto.request.user.UserCreateRequest;
import com.demo.project_intern.dto.request.user.UserSearchRequest;
import com.demo.project_intern.dto.request.user.UserUpdateRequest;
import com.demo.project_intern.dto.response.AssignRoleResponse;
import com.demo.project_intern.dto.response.RemoveRoleResponse;
import com.demo.project_intern.dto.response.ResponseData;
import com.demo.project_intern.dto.UserDto;
import com.demo.project_intern.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("${api.base-path}/user")
@Slf4j
@Tag(name = "User Controller")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("")
    @Operation(method = "POST", summary = "Create User", description = "API Create New User")
    public ResponseData<UserDto> createUser(@RequestBody @Valid UserCreateRequest request) {
        return ResponseData.<UserDto>builder()
                .message(Translator.getSuccessMessage("add", EntityType.USER))
                .data(userService.createUser(request))
                .build();
    }

    @PreAuthorize("hasAuthority('BOOK_VIEW')")
    @GetMapping("")
    @Operation(method = "GET", summary = "Get List Users", description = "API Get List Users")
    public ResponseData<List<UserDto>> getUsers() {
        return ResponseData.<List<UserDto>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.USER))
                .data(userService.getUsers())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    @Operation(method = "GET", summary = "Get Detail User", description = "API Get Detail User")
    public ResponseData<UserDto> getUser(@PathVariable("userId") Long userId) {
        return ResponseData.<UserDto>builder()
                .message(Translator.getSuccessMessage("getDetail", EntityType.USER))
                .data(userService.getUser(userId))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getPermissionOfUser/{userId}")
    @Operation(method = "GET", summary = "Get Permission Of User", description = "API Get Permission Of User")
    public ResponseData<Set<String>> getPermissionOfUser(@PathVariable("userId") Long userId) {
        return ResponseData.<Set<String>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.PERMISSION))
                .data(userService.getUserPermission(userId))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}")
    @Operation(method = "PUT", summary = "Update User", description = "API Update User")
    public ResponseData<UserDto> updateUser(@PathVariable("userId") Long userId, @RequestBody UserUpdateRequest request) {
        return ResponseData.<UserDto>builder()
                .message(Translator.getSuccessMessage("update", EntityType.USER))
                .data(userService.updateUser(userId, request))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    @Operation(method = "DELETE", summary = "Delete User", description = "API Delete User")
    public ResponseData<Void> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return ResponseData.<Void>builder()
                .message(Translator.getSuccessMessage("delete", EntityType.USER))
                .build();
    }

    @GetMapping("/my-info")
    @Operation(method = "GET", summary = "Get Info User", description = "API Get Info User")
    public ResponseData<UserDto> getMyInfo() {
        return ResponseData.<UserDto>builder()
                .message(Translator.getSuccessMessage("getInfo", EntityType.USER))
                .data(userService.getMyInfo())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/paging")
    @Operation(method = "POST", summary = "Get Paging Users", description = "API Get Paging Users")
    public ResponseData<Page<UserDto>> getPagingUsers(@RequestBody UserSearchRequest request) {
        return ResponseData.<Page<UserDto>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.USER))
                .data(userService.search(request))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/export")
    @Operation(method = "POST", summary = "Export Users", description = "API Export Users")
    public ResponseEntity<Resource> exportUsers(@RequestParam("name") String name) {
        ByteArrayOutputStream outputStream = userService.exportUser(name);
        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users_export.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/assignRole")
    @Operation(method = "POST", summary = "Assign Role To User", description = "API Assign Role To User")
    public ResponseData<AssignRoleResponse> assignRole(@RequestBody AssignRemoveRolesRequest request) {
        return ResponseData.<AssignRoleResponse>builder()
                .message(Translator.getSuccessMessage("assignRole", EntityType.USER))
                .data(userService.assignRole(request))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/removeRole")
    @Operation(method = "POST", summary = "Remove Role To User", description = "API Remove Role To User")
    public ResponseData<RemoveRoleResponse> removeRole(@RequestBody AssignRemoveRolesRequest request) {
        return ResponseData.<RemoveRoleResponse>builder()
                .message(Translator.getSuccessMessage("removeRole", EntityType.USER))
                .data(userService.removeRole(request))
                .build();
    }
}