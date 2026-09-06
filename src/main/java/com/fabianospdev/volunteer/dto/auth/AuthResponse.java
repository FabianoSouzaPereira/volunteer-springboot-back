package com.fabianospdev.volunteer.dto.auth;

public record AuthResponse(
        String token,
        String userId,
        String name,
        String email
) {
}
