package com.demo.project_intern.dto.request.user;

import com.demo.project_intern.dto.request.BaseSearchRequest;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSearchRequest extends BaseSearchRequest {
    private String userName;
    private String fullName;
    private String email;
    private String phone;
    private String address;
}