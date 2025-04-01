package com.demo.project_intern.service;

import com.demo.project_intern.dto.SearchKeywordQuery;
import com.demo.project_intern.dto.request.user.UserCreateRequest;
import com.demo.project_intern.dto.request.user.UserUpdateRequest;
import com.demo.project_intern.dto.UserDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    UserDto createUser(UserCreateRequest request);
    List<UserDto> getUsers();
    UserDto getUser(Long userId);
    UserDto updateUser(Long userId, UserUpdateRequest request);
    void deleteUser(Long userId);
    UserDto getMyInfo();
}
