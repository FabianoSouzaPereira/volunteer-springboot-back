package com.fabianospdev.volunteer.dto.member;

import com.fabianospdev.volunteer.model.MemberRole;
import com.fabianospdev.volunteer.model.MemberStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

public record MemberUpdateRequest(
        @Size(max = 120) String name,
        @Min(0) @Max(130) Integer age,
        @Size(max = 80) String group,
        MemberRole role,
        Set<MemberRole> roles,
        List<@Size(max = 80) String> functions,
        MemberStatus status,
        @Size(max = 40) String phone,
        @Email @Size(max = 160) String email,
        @Size(max = 255) String address,
        @Size(max = 80) String job
) {
}
