package com.demo.project_intern.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // khi ma field nào null sẽ ko kem vao trong json
public class ResponseData<T> {
    @Builder.Default
    private int code = 1000; // api nao response code la 1000 -> success
    private String message;
    private T data;
}