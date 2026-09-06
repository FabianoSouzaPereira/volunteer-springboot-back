package com.fabianospdev.volunteer.mapper;

import com.fabianospdev.volunteer.dto.user.UserResponse;
import com.fabianospdev.volunteer.dto.user.UserUpdateRequest;
import com.fabianospdev.volunteer.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.isEnabled(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public void apply(User user, UserUpdateRequest request) {
        if (request.name() != null) {
            user.setName(trimToNull(request.name()));
        }
        if (request.email() != null) {
            user.setEmail(normalizeEmail(request.email()));
        }
        if (request.phone() != null) {
            user.setPhone(trimToNull(request.phone()));
        }
        user.setUpdatedAt(LocalDateTime.now());
    }

    public String normalizeEmail(String email) {
        String normalized = trimToNull(email);
        return normalized == null ? null : normalized.toLowerCase();
    }

    public String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
