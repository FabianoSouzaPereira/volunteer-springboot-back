package com.fabianospdev.volunteer.usecases.pastor;

import com.fabianospdev.volunteer.models.User;
import com.fabianospdev.volunteer.repositories.UserRepository;
import com.fabianospdev.volunteer.services.exception.ObjectNotExistsException;
import com.fabianospdev.volunteer.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UseCase{

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Autowired(required=true)
    public UseCase( UserRepository userRepository, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public User insert(User user) {
        return userRepository.insert(user);
    }

    public User update(User user) {
                Optional<User> obj = userRepository.findById(user.getId());

        if (obj.isEmpty()) {
            String message = messageSource.getMessage("object.not.exists", new Object[]{user.getId()}, LocaleContextHolder.getLocale());
            throw new ObjectNotExistsException(message);
        }

        return userRepository.save(user);
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}