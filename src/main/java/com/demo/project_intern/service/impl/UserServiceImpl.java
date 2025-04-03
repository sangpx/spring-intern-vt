package com.demo.project_intern.service.impl;

import com.demo.project_intern.constant.ErrorCode;
import com.demo.project_intern.constant.PredefinedRole;
import com.demo.project_intern.dto.SearchKeywordQuery;
import com.demo.project_intern.dto.request.user.UserCreateRequest;
import com.demo.project_intern.dto.request.user.UserUpdateRequest;
import com.demo.project_intern.dto.UserDto;
import com.demo.project_intern.entity.RoleEntity;
import com.demo.project_intern.entity.UserEntity;
import com.demo.project_intern.exception.BaseLibraryException;
import com.demo.project_intern.repository.RoleRepository;
import com.demo.project_intern.repository.UserRepository;
import com.demo.project_intern.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper mapper;

    @Override
    public UserDto createUser(UserCreateRequest request) {
        UserEntity user = mapper.map(request, UserEntity.class);
        if(userRepository.existsByUserName(request.getUserName())) {
            throw new BaseLibraryException(ErrorCode.USER_EXISTED);
        }

        HashSet<RoleEntity> roles = new HashSet<>();
        roleRepository.findById(Long.valueOf(PredefinedRole.USER_ROLE)).ifPresent(roles::add);
        user.setRoles(roles);

        userRepository.save(user);
        return mapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> mapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public UserDto getUser(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(Long userId, UserUpdateRequest request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        // Check if field userName is modified
        if(!user.getUserName().equals(request.getUserName())) {
            // Kiểm tra username đã tồn tại chưa (loại trừ user hiện tại)
            boolean isUsernameTaken = userRepository.existsByUserNameAndIdNot(request.getUserName(), userId);
            if (isUsernameTaken) {
                throw new BaseLibraryException(ErrorCode.USERNAME_EXISTED);
            }
        }
        user.setUserName(request.getUserName());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        user.setDob(request.getDob());
        user.setAddress(request.getAddress());
        user.setUpdatedAt(LocalDate.now());
        userRepository.save(user);
        return mapper.map(user, UserDto.class);
    }

    @Override
    public void deleteUser(Long userId) {
        //check xem user entity co ton tai hay khong
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        userRepository.delete(user);
    }

    @Override
    public UserDto getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        UserEntity user = userRepository.findByUserName(name)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.USER_NOT_EXISTED));
        return mapper.map(user, UserDto.class);
    }
}