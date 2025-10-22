package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.dto.LeaderDTO;
import com.fabianospdev.volunteer.models.LeaderModel;
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


    public List<LeaderModel> findAll() {
        return useCase.findAll();
    }


    public List<LeaderDTO> findAllDTO() {
        return useCase.findAllDTO();
    }

    private LeaderDTO convertToLeaderDTO( LeaderModel leader ) {
        LeaderDTO leaderDTO = new LeaderDTO( leader );
        return leaderDTO;
    }


    public LeaderModel findById(String id ) {
        LeaderModel obj = useCase.findById( id );
        return obj;
    }

    public LeaderModel insert(LeaderModel obj ) {
        return useCase.insert( obj );
    }

    public void delete( String id ) {
        useCase.deleteById( id );
    }

    public LeaderModel update(LeaderModel obj ) {
        return useCase.update( obj );
    }

    private void updateData(LeaderModel newObj, LeaderModel obj ) {
        newObj.setName( obj.getName() );
        newObj.setEmail( obj.getEmail() );
    }

    public LeaderModel fromDTO(LeaderDTO objDto ) {
        return new LeaderModel( objDto.getId(), objDto.getName(), objDto.getEmail(), objDto.getPhone() );
    }
}