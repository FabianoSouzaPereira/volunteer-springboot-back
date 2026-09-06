package com.fabianospdev.volunteer.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @Size(max = 120) String name,
        @Email @Size(max = 160) String email,
        @Size(max = 40) String phone
) {
}
