package com.demo.project_intern.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String id;
    private String userName;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate dob;
    private String address;
}
