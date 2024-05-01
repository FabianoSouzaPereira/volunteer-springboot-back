package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.dto.LeaderDTO;
import com.fabianospdev.volunteer.models.Leader;
import com.fabianospdev.volunteer.repositories.LeaderRepository;
import com.fabianospdev.volunteer.usecases.leader.LeaderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderService{

    @Autowired(required = true)
    private LeaderRepository repository;

    @Autowired(required = true)
    private LeaderUseCase useCase;


    public List<Leader> findAll() {
        return useCase.findAll();
    }


    public List<LeaderDTO> findAllDTO() {
        return useCase.findAllDTO();
    }

    private LeaderDTO convertToLeaderDTO( Leader leader ) {
        LeaderDTO leaderDTO = new LeaderDTO( leader );
        return leaderDTO;
    }


    public Leader findById( String id ) {
        Leader obj = useCase.findById( id );
        return obj;
    }

    public Leader insert( Leader obj ) {
        return useCase.insert( obj );
    }

    public void delete( String id ) {
        useCase.deleteById( id );
    }

    public Leader update( Leader obj ) {
        return useCase.update( obj );
    }

    private void updateData( Leader newObj, Leader obj ) {
        newObj.setName( obj.getName() );
        newObj.setEmail( obj.getEmail() );
    }

    public Leader fromDTO( LeaderDTO objDto ) {
        return new Leader( objDto.getId(), objDto.getName(), objDto.getEmail(), objDto.getPhone() );
    }
}