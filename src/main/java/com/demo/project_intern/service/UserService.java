package com.demo.project_intern.service;

import com.demo.project_intern.dto.request.user.AssignRemoveRolesRequest;
import com.demo.project_intern.dto.request.user.UserCreateRequest;
import com.demo.project_intern.dto.request.user.UserSearchRequest;
import com.demo.project_intern.dto.request.user.UserUpdateRequest;
import com.demo.project_intern.dto.UserDto;
import com.demo.project_intern.dto.response.AssignRoleResponse;
import com.demo.project_intern.dto.response.RemoveRoleResponse;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Set;

public interface UserService extends GenericSearchService<UserSearchRequest, UserDto> {
    UserDto createUser(UserCreateRequest request);
    List<UserDto> getUsers();
    UserDto getUser(Long userId);
    UserDto updateUser(Long userId, UserUpdateRequest request);
    void deleteUser(Long userId);
    UserDto getMyInfo();
    ByteArrayOutputStream exportUser(String name);
    AssignRoleResponse assignRole(AssignRemoveRolesRequest request);
    RemoveRoleResponse removeRole(AssignRemoveRolesRequest request);
    Set<String> getUserPermission(Long userId);
}