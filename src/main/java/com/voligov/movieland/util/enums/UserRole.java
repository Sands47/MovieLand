package com.voligov.movieland.util.enums;

public enum UserRole {
    GUEST(0), USER(1), ADMIN(2);

    private int role;

    UserRole(int role) {
        this.role = role;
    }

    public static UserRole getById(int role) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.role == role) {
                return userRole;
            }
        }
        return GUEST;
    }

    public boolean equalOrHigher(UserRole requiredRole) {
        return role >= requiredRole.role;
    }
}
