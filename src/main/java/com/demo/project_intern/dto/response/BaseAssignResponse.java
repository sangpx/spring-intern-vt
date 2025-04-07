package com.demo.project_intern.dto.response;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseAssignResponse {
   private List<String> added;
   private List<String> duplicated;
}