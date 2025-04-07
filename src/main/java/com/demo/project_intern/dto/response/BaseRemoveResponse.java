package com.demo.project_intern.dto.response;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseRemoveResponse {
   private List<String> removed;
   private List<String> notAssigned;
}