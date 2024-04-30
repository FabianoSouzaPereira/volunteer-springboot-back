package com.fabianospdev.volunteer.services;



import com.fabianospdev.volunteer.dto.PastorDTO;
import com.fabianospdev.volunteer.models.Pastor;
import com.fabianospdev.volunteer.repositories.PastorRepository;
import com.fabianospdev.volunteer.usecases.pastor.PatnerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PastorService{
    @Autowired(required=true)
    private PastorRepository repository;

    @Autowired
    private PatnerUseCase useCase;


    public List<Pastor> findAll() {
        return useCase.findAll();
    }

    public Pastor findById(String id) {
        Pastor obj = useCase.findById(id);
        return obj;
    }

    public Pastor insert(Pastor obj) {
        return useCase.insert(obj);
    }

    public void delete(String id) {
        useCase.deleteById(id);
    }

    public Pastor update(Pastor obj) {
        //  Pastor newObj = findById(obj.getId());
        //  updateData(newObj, obj);
        return useCase.update(obj);
    }

    private void updateData(Pastor newObj, Pastor obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }

    public Pastor fromDTO( PastorDTO objDto) {
        return new Pastor(objDto.getId(), objDto.getName(), objDto.getEmail(), objDto.getPhone());
    }
}