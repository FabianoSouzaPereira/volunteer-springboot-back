package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.dto.auth.AuthResponse;
import com.fabianospdev.volunteer.dto.auth.LoginRequest;
import com.fabianospdev.volunteer.dto.auth.RegisterRequest;
import com.fabianospdev.volunteer.model.User;
import com.fabianospdev.volunteer.security.JwtService;
import com.fabianospdev.volunteer.services.exception.InvalidRequestException;
import com.fabianospdev.volunteer.services.exception.ObjectNotFoundException;
import com.fabianospdev.volunteer.services.exception.UnauthorizedException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final MessageSource messageSource;

    public AuthService(
            UserService userService,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            MessageSource messageSource
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.messageSource = messageSource;
    }

    public AuthResponse register(RegisterRequest request) {
        User user = userService.createUser(request.name(), request.email(), request.password(), request.phone(), true);
        return toAuthResponse(user);
    }

    public AuthResponse login(LoginRequest request) {
        User user;
        try {
            user = userService.findByEmail(request.email());
        } catch (ObjectNotFoundException | InvalidRequestException ex) {
            throw new UnauthorizedException(message("auth.invalid.credentials"));
        }

        if (!user.isEnabled() || !passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new UnauthorizedException(message("auth.invalid.credentials"));
        }

        return toAuthResponse(user);
    }

    private AuthResponse toAuthResponse(User user) {
        return new AuthResponse(jwtService.generateToken(user.getEmail()), user.getId(), user.getName(), user.getEmail());
    }

    private String message(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }
}
