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
    UNAUTHENTICATED(1006, "UnAuthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "You age must be at least {min} ", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(1009, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1010, "Invalid key", HttpStatus.BAD_REQUEST),
    CATEGORY_EXISTED(1011, "Category existed in system", HttpStatus.BAD_REQUEST),
    BOOK_EXISTED(1012, "Book existed in system", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED(1013, "Role existed in system", HttpStatus.BAD_REQUEST),
    PERMISSION_EXISTED(1014, "Permission existed in system", HttpStatus.BAD_REQUEST),
    BORROW_BOOK_EXISTED(1015, "Borrow Book existed in system", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(1016, "Category Not Found", HttpStatus.NOT_FOUND),
    USERNAME_EXISTED(1017, "Username already exists, please choose another username", HttpStatus.BAD_REQUEST),
    CODE_EXISTED(1018, "Code already exists, please choose another code", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1019, "Token invalid", HttpStatus.BAD_REQUEST),
    REQUEST_ROLES(1020, "List of role IDs cannot be empty", HttpStatus.BAD_REQUEST),
    REQUEST_ROLES_INVALID(1021, "Some role IDs are invalid", HttpStatus.BAD_REQUEST),
    FILE_VALID(1022, "The file is not a valid excel file", HttpStatus.BAD_REQUEST),
    SHEET_BOOK_NOT_FOUND(1023, "Sheet 'Books' not found in Excel file.", HttpStatus.BAD_REQUEST),
    TITLE_MISSING(1024, "Title is missing", HttpStatus.BAD_REQUEST),
    ERROR_READ_FILE(1025, "Error reading Excel file", HttpStatus.BAD_REQUEST),
    QUANTITY_VALID(1026, "Quantity must be greater than 0.", HttpStatus.BAD_REQUEST),
    NOT_ENOUGH_BOOK_QUANTITY(1027, "Not enough book quantity", HttpStatus.BAD_REQUEST),
    INVALID_EXPECTED_RETURN_DATE(1027, "Expected return date must be after borrow date", HttpStatus.BAD_REQUEST),
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
