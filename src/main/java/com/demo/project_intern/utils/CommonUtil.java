package com.demo.project_intern.utils;

import com.demo.project_intern.constant.ErrorCode;
import com.demo.project_intern.entity.UserEntity;
import com.demo.project_intern.exception.BaseLibraryException;
import com.demo.project_intern.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonUtil {

    private final UserRepository userRepository;

    public UserEntity getCurrentUser() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.USER_NOT_EXISTED));
    }
}
