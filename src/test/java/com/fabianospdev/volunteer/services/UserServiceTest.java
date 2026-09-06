package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.dto.user.PasswordChangeRequest;
import com.fabianospdev.volunteer.dto.user.UserCreateRequest;
import com.fabianospdev.volunteer.dto.user.UserResponse;
import com.fabianospdev.volunteer.mapper.UserMapper;
import com.fabianospdev.volunteer.messaging.DomainEventPublisher;
import com.fabianospdev.volunteer.messaging.KafkaTopics;
import com.fabianospdev.volunteer.model.User;
import com.fabianospdev.volunteer.repositories.UserRepository;
import com.fabianospdev.volunteer.services.exception.ForbiddenException;
import com.fabianospdev.volunteer.services.exception.ObjectAlreadyExistsException;
import com.fabianospdev.volunteer.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private DomainEventPublisher eventPublisher;

    @Mock
    private MessageSource messageSource;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, new UserMapper(), passwordEncoder, eventPublisher, messageSource);
        org.mockito.Mockito.lenient().when(messageSource.getMessage(anyString(), any(), any(Locale.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void createEncodesPasswordAndPublishesEvent() {
        UserCreateRequest request = new UserCreateRequest("Ana", "ana@example.com", "secret1", "11999999999");
        when(userRepository.existsByEmailIgnoreCase("ana@example.com")).thenReturn(false);
        when(passwordEncoder.encode("secret1")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId("u1");
            return user;
        });

        UserResponse response = userService.create(request);

        assertEquals("u1", response.id());
        assertEquals("ana@example.com", response.email());
        verify(eventPublisher).publish(eq(KafkaTopics.USER_REGISTRATION), contains("Ana"));
        verify(passwordEncoder).encode("secret1");
    }

    @Test
    void createRejectsDuplicateEmail() {
        UserCreateRequest request = new UserCreateRequest("Ana", "ana@example.com", "secret1", null);
        when(userRepository.existsByEmailIgnoreCase("ana@example.com")).thenReturn(true);

        assertThrows(ObjectAlreadyExistsException.class, () -> userService.create(request));
        verify(userRepository, never()).save(any());
    }

    @Test
    void findByEmailIsCaseInsensitive() {
        User user = User.builder().id("u1").name("Ana").email("ana@example.com").build();
        when(userRepository.findByEmailIgnoreCase("ana@example.com")).thenReturn(Optional.of(user));

        assertEquals("u1", userService.findByEmail("  ANA@example.com ").getId());
    }

    @Test
    void changePasswordValidatesCurrentPassword() {
        User user = User.builder().id("u1").password("encoded-old").build();
        when(userRepository.findById("u1")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encoded-old")).thenReturn(false);

        assertThrows(ForbiddenException.class, () ->
                userService.changePassword("u1", new PasswordChangeRequest("wrong", "newpass"))
        );
        verify(userRepository, never()).save(any());
    }

    @Test
    void deleteRequiresExistingUser() {
        when(userRepository.findById("missing")).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> userService.delete("missing"));
        verify(userRepository, never()).deleteById(anyString());
    }
}
