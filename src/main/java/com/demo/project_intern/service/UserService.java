package com.demo.project_intern.service;

import com.demo.project_intern.dto.request.UserCreateRequest;
import com.demo.project_intern.dto.request.UserUpdateRequest;
import com.demo.project_intern.dto.user.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserCreateRequest request);
    List<UserDto> getUsers();
    UserDto getUser(Long userId);
    UserDto updateUser(Long userId, UserUpdateRequest request);
    void deleteUser(Long userId);
    UserDto getMyInfo();
}
