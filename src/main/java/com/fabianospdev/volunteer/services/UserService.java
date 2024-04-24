package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.domain.models.User;
import com.fabianospdev.volunteer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }
}