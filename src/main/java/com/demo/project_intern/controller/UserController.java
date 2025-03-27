package com.demo.project_intern.controller;

import com.demo.project_intern.dto.request.user.UserCreateRequest;
import com.demo.project_intern.dto.request.user.UserUpdateRequest;
import com.demo.project_intern.dto.response.ResponseData;
import com.demo.project_intern.dto.UserDto;
import com.demo.project_intern.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
                .data(userService.createUser(request))
                .build();
    }

    @GetMapping("")
    @Operation(method = "GET", summary = "Get List Users", description = "API Get List Users")
    public ResponseData<List<UserDto>> getUsers() {
        return ResponseData.<List<UserDto>>builder()
                .data(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    @Operation(method = "GET", summary = "Get Detail User", description = "API Get Detail User")
    public ResponseData<UserDto> getUser(@PathVariable("userId") Long userId) {
        return ResponseData.<UserDto>builder()
                .data(userService.getUser(userId))
                .build();
    }

    @PutMapping("/{userId}")
    @Operation(method = "PUT", summary = "Update User", description = "API Update User")
    public ResponseData<UserDto> updateUser(@PathVariable("userId") Long userId, @RequestBody UserUpdateRequest request) {
        return ResponseData.<UserDto>builder()
                .data(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}")
    @Operation(method = "DELETE", summary = "Delete User", description = "API Delete User")
    public String deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return "success";
    }
}
