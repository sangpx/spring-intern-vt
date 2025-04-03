package com.demo.project_intern.dto.request.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshRequest {
    private String token;
}