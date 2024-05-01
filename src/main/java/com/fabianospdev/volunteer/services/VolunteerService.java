package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.dto.VolunteerDTO;
import com.fabianospdev.volunteer.models.Volunteer;
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


    public List<Volunteer> findAll() {
        return useCase.findAll();
    }

    public List<VolunteerDTO> findAllDTO() {
        return useCase.findAllDTO();
    }

    private VolunteerDTO convertToVolunteerDTO( Volunteer volunteer ) {
        VolunteerDTO volunteerDTO = new VolunteerDTO( volunteer );
        return volunteerDTO;
    }


    public Volunteer findById( String id ) {
        Volunteer obj = useCase.findById( id );
        return obj;
    }

    public Volunteer insert( Volunteer obj ) {
        return useCase.insert( obj );
    }

    public void delete( String id ) {
        useCase.deleteById( id );
    }

    public Volunteer update( Volunteer obj ) {
        return useCase.update( obj );
    }

    private void updateData( Volunteer newObj, Volunteer obj ) {
        newObj.setName( obj.getName() );
        newObj.setEmail( obj.getEmail() );
    }

    public Volunteer fromDTO( VolunteerDTO objDto ) {
        return new Volunteer( objDto.getId(), objDto.getName(), objDto.getEmail(), objDto.getPhone() );
    }
}