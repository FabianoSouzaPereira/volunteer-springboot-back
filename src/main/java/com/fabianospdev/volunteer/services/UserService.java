package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.models.User;
import com.fabianospdev.volunteer.dto.UserDTO;
import com.fabianospdev.volunteer.repositories.UserRepository;
import com.fabianospdev.volunteer.usecases.user.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired(required=true)
    private UserRepository repository;

    @Autowired
    private UserUseCase useCase;


    public List<User> findAll() {
        return useCase.findAll();
    }

    public List<UserDTO> findAllDTO(User user) {
        return useCase.findAllDTO(user);
    }


    public User findById(String id) {
        User obj = useCase.findById(id);
        return obj;
    }

    public User insert(User obj) {
        return useCase.insert(obj);
    }

    public void delete(String id) {
        useCase.deleteById(id);
    }

    public User update(User obj) {
      //  User newObj = findById(obj.getId());
      //  updateData(newObj, obj);
        return useCase.update(obj);
    }

    private void updateData(User newObj, User obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }

    public User fromDTO(UserDTO objDto) {
        return new User(objDto.getId(), objDto.getName(), objDto.getEmail(), objDto.getPhone());
    }
}