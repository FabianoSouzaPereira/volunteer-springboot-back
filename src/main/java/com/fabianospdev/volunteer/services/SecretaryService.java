package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.models.Secretary;
import com.fabianospdev.volunteer.dto.SecretaryDTO;
import com.fabianospdev.volunteer.repositories.SecretaryRepository;
import com.fabianospdev.volunteer.usecases.secretary.SecretaryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecretaryService {

    @Autowired(required=true)
    private SecretaryRepository repository;

    @Autowired(required=true)
    private SecretaryUseCase useCase;


    public List<Secretary> findAll() {
        return useCase.findAll();
    }

    public List<Secretary> findAllList() {
        return useCase.findAllList();
    }

    public List<SecretaryDTO> findAllDTO() {
        return useCase.findAllDTO();
    }

    private SecretaryDTO convertToSecretaryDTO( Secretary secretary ) {
        SecretaryDTO secretaryDTO = new SecretaryDTO(secretary);
        return secretaryDTO;
    }


    public Secretary findById(String id) {
        Secretary obj = useCase.findById(id);
        return obj;
    }

    public Secretary insert(Secretary obj) {
        return useCase.insert(obj);
    }

    public void delete(String id) {
        useCase.deleteById(id);
    }

    public Secretary update(Secretary obj) {
        return useCase.update(obj);
    }

    private void updateData(Secretary newObj, Secretary obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }

    public Secretary fromDTO(SecretaryDTO objDto) {
        return new Secretary(objDto.getId(), objDto.getName(), objDto.getEmail(), objDto.getPhone());
    }
}