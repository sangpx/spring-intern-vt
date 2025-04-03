package com.demo.project_intern.dto.request.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    @NotNull
    private String username;
    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;
}