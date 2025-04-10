package com.demo.project_intern.exception;

import com.demo.project_intern.config.Translator;
import com.demo.project_intern.constant.ErrorCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BaseLibraryException extends RuntimeException {
    private ErrorCode errorCode;
    private final Object[] args;

    public BaseLibraryException(ErrorCode errorCode, Object... args) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.args = args;
    }

    @Override
    public String getMessage() {
        return Translator.toLocale(errorCode.getMessage(), args);
    }
}
