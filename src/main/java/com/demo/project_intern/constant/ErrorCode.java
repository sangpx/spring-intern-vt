package com.demo.project_intern.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;



@Getter
public enum ErrorCode {
    //TODO: config dynamic message from translator
    USER_EXISTED(1001, "User existed in system", HttpStatus.NOT_FOUND),
    USER_NOT_EXISTED(1002, "User not existed", HttpStatus.NOT_FOUND),
    RESOURCE_NOT_FOUND(1003, "Not Found", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1004, "Username must be at least {min}", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1005, "Password must be at least {min}", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "You age must be at least {min} ", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(1009, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1010, "Invalid key", HttpStatus.BAD_REQUEST),
    CATEGORY_EXISTED(1011, "Category existed in system", HttpStatus.BAD_REQUEST),
    BOOK_EXISTED(1012, "Book existed in system", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED(1013, "Role existed in system", HttpStatus.BAD_REQUEST),
    PERMISSION_EXISTED(1014, "Permission existed in system", HttpStatus.BAD_REQUEST),
    BORROW_BOOK_EXISTED(1015, "Borrow Book existed in system", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(1016, "Category Not Found", HttpStatus.BAD_REQUEST),
    ;
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
