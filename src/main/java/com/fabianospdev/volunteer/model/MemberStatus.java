package com.fabianospdev.volunteer.model;

import com.fabianospdev.volunteer.services.exception.InvalidRequestException;

import java.util.Arrays;

public enum MemberStatus {
    ACTIVE,
    INACTIVE;

    public static MemberStatus fromValue(String value) {
        if (value == null || value.isBlank()) {
            return ACTIVE;
        }

        return Arrays.stream(values())
                .filter(status -> status.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new InvalidRequestException("Unknown member status: " + value));
    }
}
