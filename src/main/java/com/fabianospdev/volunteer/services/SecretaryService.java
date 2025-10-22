package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.dto.SecretaryDTO;
import com.fabianospdev.volunteer.models.SecretaryModel;
import com.fabianospdev.volunteer.repositories.SecretaryRepository;
import com.fabianospdev.volunteer.usecases.secretary.SecretaryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecretaryService{

    @Autowired(required = true)
    private SecretaryRepository repository;

    @Autowired(required = true)
    private SecretaryUseCase useCase;


    public List<SecretaryModel> findAll() {
        return useCase.findAll();
    }

    public List<SecretaryDTO> findAllDTO() {
        return useCase.findAllDTO();
    }

    private SecretaryDTO convertToSecretaryDTO( SecretaryModel secretary ) {
        SecretaryDTO secretaryDTO = new SecretaryDTO( secretary );
        return secretaryDTO;
    }


    public SecretaryModel findById(String id ) {
        SecretaryModel obj = useCase.findById( id );
        return obj;
    }

    public SecretaryModel insert(SecretaryModel obj ) {
        return useCase.insert( obj );
    }

    public void delete( String id ) {
        useCase.deleteById( id );
    }

    public SecretaryModel update(SecretaryModel obj ) {
        return useCase.update( obj );
    }

    private void updateData(SecretaryModel newObj, SecretaryModel obj ) {
        newObj.setName( obj.getName() );
        newObj.setEmail( obj.getEmail() );
    }

    public SecretaryModel fromDTO(SecretaryDTO objDto ) {
        return new SecretaryModel( objDto.getId(), objDto.getName(), objDto.getEmail(), objDto.getPhone() );
    }
}