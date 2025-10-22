package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.dto.UserDTO;
import com.fabianospdev.volunteer.models.UserModel;
import com.fabianospdev.volunteer.repositories.UserRepository;
import com.fabianospdev.volunteer.usecases.user.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{

    @Autowired(required = true)
    private UserRepository repository;

    @Autowired(required = true)
    private UserUseCase useCase;


    public List<UserModel> findAll() {
        return useCase.findAll();
    }


    public List<UserDTO> findAllDTO() {
        return useCase.findAllDTO();
    }

    private UserDTO convertToUserDTO( UserModel user ) {
        UserDTO userDTO = new UserDTO( user );
        return userDTO;
    }


    public UserModel findById(String id ) {
        UserModel obj = useCase.findById( id );
        return obj;
    }

    public UserModel insert(UserModel obj ) {
        return useCase.insert( obj );
    }

    public void delete( String id ) {
        useCase.deleteById( id );
    }

    public UserModel update(UserModel obj ) {
        return useCase.update( obj );
    }

    private void updateData(UserModel newObj, UserModel obj ) {
        newObj.setName( obj.getName() );
        newObj.setEmail( obj.getEmail() );
    }

    public UserModel fromDTO(UserDTO objDto ) {
        return new UserModel( objDto.getId(), objDto.getName(), objDto.getEmail(), objDto.getPhone() );
    }
}