package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.dto.auth.AuthResponse;
import com.fabianospdev.volunteer.dto.auth.LoginRequest;
import com.fabianospdev.volunteer.dto.auth.RegisterRequest;
import com.fabianospdev.volunteer.model.User;
import com.fabianospdev.volunteer.security.JwtService;
import com.fabianospdev.volunteer.services.exception.ObjectNotFoundException;
import com.fabianospdev.volunteer.services.exception.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private MessageSource messageSource;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(userService, passwordEncoder, jwtService, messageSource);
        org.mockito.Mockito.lenient().when(messageSource.getMessage(anyString(), any(), any(Locale.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void registerReturnsToken() {
        User user = User.builder().id("u1").name("Ana").email("ana@example.com").build();
        when(userService.createUser("Ana", "ana@example.com", "secret1", null, true)).thenReturn(user);
        when(jwtService.generateToken("ana@example.com")).thenReturn("token-1");

        AuthResponse response = authService.register(new RegisterRequest("Ana", "ana@example.com", "secret1", null));

        assertEquals("token-1", response.token());
        assertEquals("u1", response.userId());
    }

    @Test
    void loginReturnsTokenWhenCredentialsMatch() {
        User user = User.builder()
                .id("u1")
                .name("Ana")
                .email("ana@example.com")
                .password("encoded")
                .enabled(true)
                .build();
        when(userService.findByEmail("ana@example.com")).thenReturn(user);
        when(passwordEncoder.matches("secret1", "encoded")).thenReturn(true);
        when(jwtService.generateToken("ana@example.com")).thenReturn("token-1");

        AuthResponse response = authService.login(new LoginRequest("ana@example.com", "secret1"));

        assertEquals("token-1", response.token());
        assertEquals("Ana", response.name());
    }

    @Test
    void loginHidesMissingUser() {
        when(userService.findByEmail("missing@example.com")).thenThrow(new ObjectNotFoundException("not found"));

        assertThrows(UnauthorizedException.class, () ->
                authService.login(new LoginRequest("missing@example.com", "secret1"))
        );
    }

    @Test
    void loginRejectsInvalidPassword() {
        User user = User.builder()
                .id("u1")
                .email("ana@example.com")
                .password("encoded")
                .enabled(true)
                .build();
        when(userService.findByEmail("ana@example.com")).thenReturn(user);
        when(passwordEncoder.matches("wrong", "encoded")).thenReturn(false);

        assertThrows(UnauthorizedException.class, () ->
                authService.login(new LoginRequest("ana@example.com", "wrong"))
        );
    }
}
