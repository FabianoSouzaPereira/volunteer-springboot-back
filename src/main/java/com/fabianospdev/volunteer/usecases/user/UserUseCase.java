package com.fabianospdev.volunteer.usecases.user;

import com.fabianospdev.volunteer.dto.UserDTO;
import com.fabianospdev.volunteer.models.User;
import com.fabianospdev.volunteer.repositories.UserRepository;
import com.fabianospdev.volunteer.services.exception.ObjectNotExistsException;
import com.fabianospdev.volunteer.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserUseCase{
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired(required=true)
    public UserUseCase(UserRepository userRepository, MessageSource messageSource, KafkaTemplate<String, String> kafkaTemplate) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<UserDTO> findAllDTO() {
        return userRepository.findAllDTO();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public User insert(User user) {
        Optional<User> success = Optional.ofNullable(userRepository.insert(user));

        if(success.isPresent()) {
            String message = messageSource.getMessage("object.insert.success", new Object[]{user.getId()}, LocaleContextHolder.getLocale());
            kafkaTemplate.send("user-registration-events", message + user.getName());
        }

        return success.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public User update(User user) {
        Optional<User> obj = userRepository.findById(user.getId());

        if(obj.isEmpty()) {
            String message = messageSource.getMessage("object.not.exists", new Object[]{user.getId()}, LocaleContextHolder.getLocale());
            throw new ObjectNotExistsException(message);
        }

        return userRepository.save(user);
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    private UserDTO convertToUserDTO(User user) {

        if(user == null) {
            return new UserDTO();
        }

        return new UserDTO(user);
    }
}