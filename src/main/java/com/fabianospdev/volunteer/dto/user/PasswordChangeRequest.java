package com.fabianospdev.volunteer.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordChangeRequest(
        @NotBlank String oldPassword,
        @NotBlank @Size(min = 6, max = 72) String newPassword
) {
}
