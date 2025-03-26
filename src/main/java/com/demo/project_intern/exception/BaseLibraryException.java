package com.demo.project_intern.exception;

import com.demo.project_intern.constant.ErrorCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BaseLibraryException extends RuntimeException {
    private ErrorCode errorCode;

    public BaseLibraryException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
