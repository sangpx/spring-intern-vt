package com.demo.project_intern.controller;

import com.demo.project_intern.config.Translator;
import com.demo.project_intern.constant.EntityType;
import com.demo.project_intern.dto.request.user.UserCreateRequest;
import com.demo.project_intern.dto.request.user.UserSearchRequest;
import com.demo.project_intern.dto.request.user.UserUpdateRequest;
import com.demo.project_intern.dto.response.ResponseData;
import com.demo.project_intern.dto.UserDto;
import com.demo.project_intern.service.UserService;
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

//    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAuthority('BOOK_VIEW')")
    @GetMapping("")
    @Operation(method = "GET", summary = "Get List Users", description = "API Get List Users")
    public ResponseData<List<UserDto>> getUsers() {
        return ResponseData.<List<UserDto>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.USER))
                .data(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    @Operation(method = "GET", summary = "Get Detail User", description = "API Get Detail User")
    public ResponseData<UserDto> getUser(@PathVariable("userId") Long userId) {
        return ResponseData.<UserDto>builder()
                .message(Translator.getSuccessMessage("getDetail", EntityType.USER))
                .data(userService.getUser(userId))
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
    public String deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return "Deleted Successfully!";
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
}
