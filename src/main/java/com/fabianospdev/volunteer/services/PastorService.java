package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.dto.PastorDTO;
import com.fabianospdev.volunteer.models.PastorModel;
import com.fabianospdev.volunteer.repositories.PastorRepository;
import com.fabianospdev.volunteer.usecases.pastor.PastorUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PastorService{

    @Autowired(required = true)
    private PastorRepository repository;

    @Autowired(required = true)
    private PastorUseCase useCase;


    public List<PastorModel> findAll() {
        return useCase.findAll();
    }


    public List<PastorDTO> findAllDTO() {
        return useCase.findAllDTO();
    }

    private PastorDTO convertToPastorDTO( PastorModel pastor ) {
        PastorDTO pastorDTO = new PastorDTO( pastor );
        return pastorDTO;
    }


    public PastorModel findById(String id ) {
        PastorModel obj = useCase.findById( id );
        return obj;
    }

    public PastorModel insert(PastorModel obj ) {
        return useCase.insert( obj );
    }

    public void delete( String id ) {
        useCase.deleteById( id );
    }

    public PastorModel update(PastorModel obj ) {
        return useCase.update( obj );
    }

    private void updateData(PastorModel newObj, PastorModel obj ) {
        newObj.setName( obj.getName() );
        newObj.setEmail( obj.getEmail() );
    }

    public PastorModel fromDTO(PastorDTO objDto ) {
        return new PastorModel( objDto.getId(), objDto.getName(), objDto.getEmail(), objDto.getPhone() );
    }
}