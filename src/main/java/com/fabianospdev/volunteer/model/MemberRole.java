package com.fabianospdev.volunteer.model;

import com.fabianospdev.volunteer.services.exception.InvalidRequestException;

import java.util.Arrays;
import java.util.Locale;

public enum MemberRole {
    VOLUNTEER,
    LEADER,
    PASTOR,
    SECRETARY,
    EMPLOYEE,
    PARTNER;

    public static MemberRole fromPath(String path) {
        if (path == null) {
            throw new InvalidRequestException("Unknown member role path");
        }

        return switch (path.toLowerCase(Locale.ROOT)) {
            case "volunteers" -> VOLUNTEER;
            case "leaders" -> LEADER;
            case "pastors" -> PASTOR;
            case "secretaries" -> SECRETARY;
            case "employees" -> EMPLOYEE;
            case "partners" -> PARTNER;
            default -> throw new InvalidRequestException("Unknown member role path: " + path);
        };
    }

    public static MemberRole fromValue(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return Arrays.stream(values())
                .filter(role -> role.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new InvalidRequestException("Unknown member role: " + value));
    }
}
