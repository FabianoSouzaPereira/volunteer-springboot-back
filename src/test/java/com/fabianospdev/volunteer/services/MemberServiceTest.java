package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.dto.member.MemberCreateRequest;
import com.fabianospdev.volunteer.dto.member.MemberResponse;
import com.fabianospdev.volunteer.dto.member.MemberUpdateRequest;
import com.fabianospdev.volunteer.mapper.MemberMapper;
import com.fabianospdev.volunteer.model.Member;
import com.fabianospdev.volunteer.model.MemberRole;
import com.fabianospdev.volunteer.model.MemberStatus;
import com.fabianospdev.volunteer.repositories.MemberRepository;
import com.fabianospdev.volunteer.services.exception.InvalidRequestException;
import com.fabianospdev.volunteer.services.exception.ObjectAlreadyExistsException;
import com.fabianospdev.volunteer.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MessageSource messageSource;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository, new MemberMapper(), messageSource);
        org.mockito.Mockito.lenient().when(messageSource.getMessage(anyString(), any(), any(Locale.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void createPersistsMemberWithPathRole() {
        MemberCreateRequest request = new MemberCreateRequest(
                "Ana", 32, "Grupo A", null, null, List.of("Recepcao"), MemberStatus.ACTIVE,
                "11999999999", "ana@example.com", "Rua 1", "Professora"
        );
        when(memberRepository.existsByEmailIgnoreCase("ana@example.com")).thenReturn(false);
        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> {
            Member member = invocation.getArgument(0);
            member.setId("m1");
            return member;
        });

        MemberResponse response = memberService.create(request, MemberRole.LEADER);

        assertEquals("m1", response.id());
        assertEquals("Ana", response.name());
        assertTrue(response.roles().contains(MemberRole.LEADER));
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    void createRejectsDuplicateEmail() {
        MemberCreateRequest request = new MemberCreateRequest(
                "Ana", null, null, MemberRole.VOLUNTEER, null, null, null,
                null, "ana@example.com", null, null
        );
        when(memberRepository.existsByEmailIgnoreCase("ana@example.com")).thenReturn(true);

        assertThrows(ObjectAlreadyExistsException.class, () -> memberService.create(request, null));
        verify(memberRepository, never()).save(any());
    }

    @Test
    void createRequiresAtLeastOneRole() {
        MemberCreateRequest request = new MemberCreateRequest(
                "Ana", null, null, null, null, null, null, null, null, null, null
        );

        assertThrows(InvalidRequestException.class, () -> memberService.create(request, null));
    }

    @Test
    void findByIdFiltersByRole() {
        Member member = Member.builder()
                .id("m1")
                .name("Joao")
                .roles(EnumSet.of(MemberRole.VOLUNTEER))
                .status(MemberStatus.ACTIVE)
                .build();
        when(memberRepository.findById("m1")).thenReturn(Optional.of(member));

        assertThrows(ObjectNotFoundException.class, () -> memberService.findById("m1", MemberRole.PASTOR));
        assertEquals("Joao", memberService.findById("m1", MemberRole.VOLUNTEER).name());
    }

    @Test
    void deleteOnAliasRemovesOnlyThatRole() {
        Member member = Member.builder()
                .id("m1")
                .name("Joao")
                .roles(EnumSet.of(MemberRole.VOLUNTEER, MemberRole.LEADER))
                .status(MemberStatus.ACTIVE)
                .build();
        when(memberRepository.findById("m1")).thenReturn(Optional.of(member));

        memberService.delete("m1", MemberRole.LEADER);

        verify(memberRepository).save(member);
        verify(memberRepository, never()).deleteById(anyString());
        assertEquals(Set.of(MemberRole.VOLUNTEER), member.getRoles());
    }

    @Test
    void deleteRemovesMemberWhenLastRoleIsRemoved() {
        Member member = Member.builder()
                .id("m1")
                .name("Joao")
                .roles(EnumSet.of(MemberRole.LEADER))
                .status(MemberStatus.ACTIVE)
                .build();
        when(memberRepository.findById("m1")).thenReturn(Optional.of(member));

        memberService.delete("m1", MemberRole.LEADER);

        verify(memberRepository).deleteById("m1");
    }

    @Test
    void updateChangesName() {
        Member member = Member.builder()
                .id("m1")
                .name("Joao")
                .email("joao@example.com")
                .roles(EnumSet.of(MemberRole.VOLUNTEER))
                .status(MemberStatus.ACTIVE)
                .build();
        when(memberRepository.findById("m1")).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MemberUpdateRequest request = new MemberUpdateRequest(
                "Joao Silva", null, null, null, null, null, null, null, null, null, null
        );

        MemberResponse response = memberService.update("m1", request, null);

        assertEquals("Joao Silva", response.name());
        verify(memberRepository).save(eq(member));
    }
}
