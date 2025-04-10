package com.demo.project_intern.constant;

import com.demo.project_intern.config.SpringContext;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED(1001, "error.user.existed", HttpStatus.NOT_FOUND),
    USER_NOT_EXISTED(1002, "error.user.notExisted", HttpStatus.NOT_FOUND),
    RESOURCE_NOT_FOUND(1003, "error.resource.notFound", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1004, "error.username.invalid", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1005, "error.password.invalid", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "error.unAuthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "error.unAuthorized", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "error.age.invalid", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(1009, "error.exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1010, "error.invalid.key", HttpStatus.BAD_REQUEST),
    CATEGORY_EXISTED(1011, "error.category.existed", HttpStatus.BAD_REQUEST),
    BOOK_EXISTED(1012, "error.book.existed", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED(1013, "error.role.existed", HttpStatus.BAD_REQUEST),
    PERMISSION_EXISTED(1014, "error.permission.existed", HttpStatus.BAD_REQUEST),
    BORROW_BOOK_EXISTED(1015, "error.borrowBook.existed", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(1016, "error.category.notFound", HttpStatus.NOT_FOUND),
    USERNAME_EXISTED(1017, "error.user.username.existed", HttpStatus.BAD_REQUEST),
    CODE_EXISTED(1018, "error.code.existed", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1019, "error.token.invalid", HttpStatus.BAD_REQUEST),
    REQUEST_ROLES(1020, "error.role.request.roleIds", HttpStatus.BAD_REQUEST),
    REQUEST_ROLES_INVALID(1021, "error.role.invalid.roleIds", HttpStatus.BAD_REQUEST),
    FILE_VALID(1022, "error.invalid.excel.file", HttpStatus.BAD_REQUEST),
    SHEET_BOOK_NOT_FOUND(1023, "error.sheet.notfound", HttpStatus.BAD_REQUEST),
    TITLE_MISSING(1024, "error.title.missing", HttpStatus.BAD_REQUEST),
    ERROR_READ_FILE(1025, "error.read.excel.file", HttpStatus.BAD_REQUEST),
    QUANTITY_VALID(1026, "error.borrowBook.quantity", HttpStatus.BAD_REQUEST),
    NOT_ENOUGH_BOOK_QUANTITY(1027, "error.borrowBook.quantity.enough", HttpStatus.BAD_REQUEST),
    INVALID_EXPECTED_RETURN_DATE(1028, "error.borrowBook.expected", HttpStatus.BAD_REQUEST),
    FAILED_EXPORT_EXCEL(1029, "error.read.excel.exportData", HttpStatus.BAD_REQUEST),
    REQUEST_CATEGORIES_INVALID(1030, "error.category.invalid.categoryIds", HttpStatus.BAD_REQUEST),
    REQUEST_PERMISSIONS_INVALID(1032, "error.permission.invalid.permissionIds", HttpStatus.BAD_REQUEST),
    ;
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage(Object... args) {
        return SpringContext.getBean(MessageSource.class)
                .getMessage(this.message, args, LocaleContextHolder.getLocale());
    }
}
