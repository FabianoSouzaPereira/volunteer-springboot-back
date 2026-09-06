package com.fabianospdev.volunteer.dto.member;

import com.fabianospdev.volunteer.model.MemberRole;
import com.fabianospdev.volunteer.model.MemberStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MemberResponse(
        String id,
        String name,
        Integer age,
        String group,
        MemberRole role,
        Set<MemberRole> roles,
        List<String> functions,
        MemberStatus status,
        String phone,
        String email,
        String address,
        String job,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
