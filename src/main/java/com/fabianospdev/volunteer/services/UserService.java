package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.dto.user.PasswordChangeRequest;
import com.fabianospdev.volunteer.dto.user.UserCreateRequest;
import com.fabianospdev.volunteer.dto.user.UserResponse;
import com.fabianospdev.volunteer.dto.user.UserUpdateRequest;
import com.fabianospdev.volunteer.mapper.UserMapper;
import com.fabianospdev.volunteer.messaging.DomainEventPublisher;
import com.fabianospdev.volunteer.messaging.KafkaTopics;
import com.fabianospdev.volunteer.model.User;
import com.fabianospdev.volunteer.repositories.UserRepository;
import com.fabianospdev.volunteer.services.exception.ForbiddenException;
import com.fabianospdev.volunteer.services.exception.InvalidRequestException;
import com.fabianospdev.volunteer.services.exception.ObjectAlreadyExistsException;
import com.fabianospdev.volunteer.services.exception.ObjectNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final DomainEventPublisher eventPublisher;
    private final MessageSource messageSource;

    public UserService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            DomainEventPublisher eventPublisher,
            MessageSource messageSource
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
        this.messageSource = messageSource;
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(userMapper::toResponse).toList();
    }

    public UserResponse findById(String id) {
        return userMapper.toResponse(findEntity(id));
    }

    public UserResponse create(UserCreateRequest request) {
        User user = createUser(request.name(), request.email(), request.password(), request.phone(), true);
        return userMapper.toResponse(user);
    }

    public User createUser(String name, String email, String rawPassword, String phone, boolean publishEvent) {
        String normalizedEmail = userMapper.normalizeEmail(email);
        if (normalizedEmail == null) {
            throw new InvalidRequestException(message("user.email.required"));
        }
        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new ObjectAlreadyExistsException(message("user.email.exists", normalizedEmail));
        }

        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .name(userMapper.trimToNull(name))
                .email(normalizedEmail)
                .phone(userMapper.trimToNull(phone))
                .password(passwordEncoder.encode(rawPassword))
                .enabled(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        User saved = userRepository.save(user);
        if (publishEvent) {
            eventPublisher.publish(KafkaTopics.USER_REGISTRATION, message("object.insert.success") + saved.getName());
        }
        return saved;
    }

    public UserResponse update(String id, UserUpdateRequest request) {
        User user = findEntity(id);
        String email = userMapper.normalizeEmail(request.email());
        if (email != null && userRepository.existsByEmailIgnoreCaseAndIdNot(email, id)) {
            throw new ObjectAlreadyExistsException(message("user.email.exists", email));
        }
        userMapper.apply(user, request);
        return userMapper.toResponse(userRepository.save(user));
    }

    public void changePassword(String id, PasswordChangeRequest request) {
        User user = findEntity(id);
        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new ForbiddenException(message("password.mismatch"));
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void delete(String id) {
        findEntity(id);
        userRepository.deleteById(id);
    }

    public User findByEmail(String email) {
        String normalizedEmail = userMapper.normalizeEmail(email);
        if (normalizedEmail == null) {
            throw new InvalidRequestException(message("user.email.required"));
        }
        return userRepository.findByEmailIgnoreCase(normalizedEmail)
                .orElseThrow(() -> new ObjectNotFoundException(message("auth.user.not.found")));
    }

    private User findEntity(String id) {
        if (id == null || id.isBlank()) {
            throw new InvalidRequestException(message("id.required"));
        }
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(message("object.not.found", id)));
    }

    private String message(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
