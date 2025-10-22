package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.dto.VolunteerDTO;
import com.fabianospdev.volunteer.models.VolunteerModel;
import com.fabianospdev.volunteer.repositories.VolunteerRepository;
import com.fabianospdev.volunteer.usecases.volunteer.VolunteerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerService{

    @Autowired(required = true)
    private VolunteerRepository repository;

    @Autowired(required = true)
    private VolunteerUseCase useCase;


    public List<VolunteerModel> findAll() {
        return useCase.findAll();
    }

    public List<VolunteerDTO> findAllDTO() {
        return useCase.findAllDTO();
    }

    private VolunteerDTO convertToVolunteerDTO( VolunteerModel volunteer ) {
        VolunteerDTO volunteerDTO = new VolunteerDTO( volunteer );
        return volunteerDTO;
    }


    public VolunteerModel findById(String id ) {
        VolunteerModel obj = useCase.findById( id );
        return obj;
    }

    public VolunteerModel insert(VolunteerModel obj ) {
        return useCase.insert( obj );
    }

    public void delete( String id ) {
        useCase.deleteById( id );
    }

    public VolunteerModel update(VolunteerModel obj ) {
        return useCase.update( obj );
    }

    private void updateData(VolunteerModel newObj, VolunteerModel obj ) {
        newObj.setName( obj.getName() );
        newObj.setEmail( obj.getEmail() );
    }

    public VolunteerModel fromDTO(VolunteerDTO objDto ) {
        return new VolunteerModel( objDto.getId(), objDto.getName(), objDto.getEmail(), objDto.getPhone() );
    }
}