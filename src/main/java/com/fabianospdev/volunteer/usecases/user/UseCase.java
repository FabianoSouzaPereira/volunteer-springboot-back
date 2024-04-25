package com.fabianospdev.volunteer.usecases.user;

import com.fabianospdev.volunteer.models.User;
import com.fabianospdev.volunteer.repositories.UserRepository;
import com.fabianospdev.volunteer.services.exception.ObjectNotFoundException;
import com.fabianospdev.volunteer.services.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UseCase{

    private final UserRepository userRepository;

    @Autowired(required=true)
    public UseCase( UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public User insert(User user) {

        if (userRepository.existsById(user.getId())) {
            throw new UserAlreadyExistsException("User with id " + user.getId() + " already exists.");
        }

        return userRepository.insert(user);
    }

    public User update(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new UserAlreadyExistsException("User with id " + user.getId() + " already not exists.");
        }
        return userRepository.save(user);
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}