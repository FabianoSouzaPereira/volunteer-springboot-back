package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.dto.member.MemberCreateRequest;
import com.fabianospdev.volunteer.dto.member.MemberResponse;
import com.fabianospdev.volunteer.dto.member.MemberUpdateRequest;
import com.fabianospdev.volunteer.mapper.MemberMapper;
import com.fabianospdev.volunteer.model.Member;
import com.fabianospdev.volunteer.model.MemberRole;
import com.fabianospdev.volunteer.repositories.MemberRepository;
import com.fabianospdev.volunteer.services.exception.InvalidRequestException;
import com.fabianospdev.volunteer.services.exception.ObjectAlreadyExistsException;
import com.fabianospdev.volunteer.services.exception.ObjectNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final MessageSource messageSource;

    public MemberService(MemberRepository memberRepository, MemberMapper memberMapper, MessageSource messageSource) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
        this.messageSource = messageSource;
    }

    public List<MemberResponse> findAll(MemberRole role) {
        List<Member> members = role == null
                ? memberRepository.findAll()
                : memberRepository.findByRolesContaining(role);
        return members.stream().map(memberMapper::toResponse).toList();
    }

    public MemberResponse findById(String id, MemberRole requiredRole) {
        Member member = findEntity(id);
        ensureRole(member, requiredRole);
        return memberMapper.toResponse(member);
    }

    public MemberResponse create(MemberCreateRequest request, MemberRole pathRole) {
        Set<MemberRole> roles = memberMapper.resolveRoles(pathRole, request.role(), request.roles());
        String email = normalizeEmail(request.email());
        if (email != null && memberRepository.existsByEmailIgnoreCase(email)) {
            throw new ObjectAlreadyExistsException(message("member.email.exists", email));
        }

        Member member = memberMapper.toNewEntity(request, roles);
        return memberMapper.toResponse(memberRepository.save(member));
    }

    public MemberResponse update(String id, MemberUpdateRequest request, MemberRole pathRole) {
        Member member = findEntity(id);
        ensureRole(member, pathRole);

        String email = normalizeEmail(request.email());
        if (email != null && memberRepository.existsByEmailIgnoreCaseAndIdNot(email, id)) {
            throw new ObjectAlreadyExistsException(message("member.email.exists", email));
        }

        memberMapper.apply(member, request);
        if (request.roles() != null || request.role() != null || pathRole != null) {
            Set<MemberRole> roles = memberMapper.copyRoles(member.getRoles());
            if (request.roles() != null || request.role() != null) {
                roles = memberMapper.resolveRoles(pathRole, request.role(), request.roles());
            } else if (pathRole != null) {
                roles.add(pathRole);
            }
            member.setRoles(roles);
        }

        member.setUpdatedAt(LocalDateTime.now());
        return memberMapper.toResponse(memberRepository.save(member));
    }

    public void delete(String id, MemberRole pathRole) {
        Member member = findEntity(id);
        if (pathRole == null) {
            memberRepository.deleteById(id);
            return;
        }

        ensureRole(member, pathRole);
        member.getRoles().remove(pathRole);
        if (member.getRoles().isEmpty()) {
            memberRepository.deleteById(id);
            return;
        }

        member.setUpdatedAt(LocalDateTime.now());
        memberRepository.save(member);
    }

    private Member findEntity(String id) {
        if (id == null || id.isBlank()) {
            throw new InvalidRequestException(message("id.required"));
        }
        return memberRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(message("object.not.found", id)));
    }

    private void ensureRole(Member member, MemberRole requiredRole) {
        if (requiredRole != null && (member.getRoles() == null || !member.getRoles().contains(requiredRole))) {
            throw new ObjectNotFoundException(message("object.not.found", member.getId()));
        }
    }

    private String normalizeEmail(String email) {
        if (email == null || email.isBlank()) {
            return null;
        }
        return email.trim().toLowerCase();
    }

    private String message(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
