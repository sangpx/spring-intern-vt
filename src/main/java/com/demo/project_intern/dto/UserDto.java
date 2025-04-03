package com.demo.project_intern.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

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
    @JsonIgnore
    private Set<RoleDto> roles;
}
