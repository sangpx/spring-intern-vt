
package com.demo.project_intern.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    @NotNull
    private String userName;
    private String fullName;
    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;
    @Email
    private String email;
    private String phone;
    private LocalDate dob;
}
