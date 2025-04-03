package com.demo.project_intern.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntrospectResponse {
   private boolean valid;
}