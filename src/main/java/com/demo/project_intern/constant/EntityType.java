package com.demo.project_intern.constant;

import lombok.Getter;

@Getter
public enum EntityType {
    USER("User"),
    CATEGORY("Category"),
    POST("Post"),
    ROLE("Role"),
    PERMISSION("Permission"),
    COMMENT("Comment"),
    POST_LIKE("Post_Like"),
    BORROW_BOOK("Borrow_Book"),
    BORROW_DETAIL("Borrow_Detail"),
    BOOK("Book");

    private final String displayName;

    EntityType(String displayName) {
        this.displayName = displayName;
    }

}
