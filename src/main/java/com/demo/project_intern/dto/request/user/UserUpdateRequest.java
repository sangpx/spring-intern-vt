package com.demo.project_intern.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequest {
    private String userName;
    private String fullName;
    private String email;
    private String phone;
    private String password;
    private LocalDate dob;
}
