package com.fabianospdev.volunteer.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(
        String id,
        String name,
        String email,
        String phone,
        boolean enabled,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
