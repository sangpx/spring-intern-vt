package com.demo.project_intern.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String userName;
    private String fullName;
    private String email;
    private String phone;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LocalDate dob;
    private String address;
    @JsonIgnore
    private Set<RoleDto> roles;

    public UserDto(String userName, String fullName, String email, String phone, String address) {
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}
