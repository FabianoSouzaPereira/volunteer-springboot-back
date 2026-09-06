package com.fabianospdev.volunteer.mapper;

import com.fabianospdev.volunteer.dto.member.MemberCreateRequest;
import com.fabianospdev.volunteer.dto.member.MemberResponse;
import com.fabianospdev.volunteer.dto.member.MemberUpdateRequest;
import com.fabianospdev.volunteer.model.Member;
import com.fabianospdev.volunteer.model.MemberRole;
import com.fabianospdev.volunteer.model.MemberStatus;
import com.fabianospdev.volunteer.services.exception.InvalidRequestException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Component
public class MemberMapper {

    public Member toNewEntity(MemberCreateRequest request, Set<MemberRole> roles) {
        LocalDateTime now = LocalDateTime.now();
        return Member.builder()
                .name(trimToNull(request.name()))
                .age(request.age())
                .group(trimToNull(request.group()))
                .roles(copyRoles(roles))
                .functions(copyFunctions(request.functions()))
                .status(request.status() != null ? request.status() : MemberStatus.ACTIVE)
                .phone(trimToNull(request.phone()))
                .email(normalizeEmail(request.email()))
                .address(trimToNull(request.address()))
                .job(trimToNull(request.job()))
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public void apply(Member member, MemberUpdateRequest request) {
        if (request.name() != null) {
            member.setName(trimToNull(request.name()));
        }
        if (request.age() != null) {
            member.setAge(request.age());
        }
        if (request.group() != null) {
            member.setGroup(trimToNull(request.group()));
        }
        if (request.functions() != null) {
            member.setFunctions(copyFunctions(request.functions()));
        }
        if (request.status() != null) {
            member.setStatus(request.status());
        }
        if (request.phone() != null) {
            member.setPhone(trimToNull(request.phone()));
        }
        if (request.email() != null) {
            member.setEmail(normalizeEmail(request.email()));
        }
        if (request.address() != null) {
            member.setAddress(trimToNull(request.address()));
        }
        if (request.job() != null) {
            member.setJob(trimToNull(request.job()));
        }
        member.setUpdatedAt(LocalDateTime.now());
    }

    public MemberResponse toResponse(Member member) {
        Set<MemberRole> roles = copyRoles(member.getRoles());
        MemberRole primary = roles.stream().findFirst().orElse(null);
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getAge(),
                member.getGroup(),
                primary,
                roles,
                member.getFunctions() != null ? List.copyOf(member.getFunctions()) : List.of(),
                member.getStatus(),
                member.getPhone(),
                member.getEmail(),
                member.getAddress(),
                member.getJob(),
                member.getCreatedAt(),
                member.getUpdatedAt()
        );
    }

    public Set<MemberRole> resolveRoles(MemberRole pathRole, MemberRole requestRole, Set<MemberRole> requestRoles) {
        Set<MemberRole> roles = EnumSet.noneOf(MemberRole.class);
        if (requestRoles != null) {
            roles.addAll(requestRoles);
        }
        if (requestRole != null) {
            roles.add(requestRole);
        }
        if (pathRole != null) {
            roles.add(pathRole);
        }
        if (roles.isEmpty()) {
            throw new InvalidRequestException("At least one member role is required");
        }
        return roles;
    }

    public Set<MemberRole> copyRoles(Set<MemberRole> roles) {
        if (roles == null || roles.isEmpty()) {
            return EnumSet.noneOf(MemberRole.class);
        }
        return EnumSet.copyOf(roles);
    }

    private List<String> copyFunctions(List<String> functions) {
        if (functions == null) {
            return new ArrayList<>();
        }
        return functions.stream()
                .map(this::trimToNull)
                .filter(value -> value != null)
                .toList();
    }

    private String normalizeEmail(String email) {
        String normalized = trimToNull(email);
        return normalized == null ? null : normalized.toLowerCase();
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
