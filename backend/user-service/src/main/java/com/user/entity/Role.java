package com.user.entity;

public enum Role {
    OWNER,
    MANAGER,
    ENGINEER,
    PRODUCT_MANAGER,
    USER;

    public static Role convertRoleFromString(String role) {
        try {
            return Role.valueOf(role);
        } catch (IllegalArgumentException e) {
            return Role.USER;
        }
    }
}
