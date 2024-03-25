package com.backend.online_qwiz.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    STUDENT_READ("student:read"),
    STUDENT_UPDATE("student:submit"),

    ;

    @Getter
    private final String permission;
}
