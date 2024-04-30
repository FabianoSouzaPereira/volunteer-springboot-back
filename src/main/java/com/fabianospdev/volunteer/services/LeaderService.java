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
    @Autowired(required=true)
    private LeaderRepository repository;

    @Autowired
    private LeaderUseCase useCase;


    public List<Leader> findAll() {
        return useCase.findAll();
    }

    public Leader findById(String id) {
        Leader obj = useCase.findById(id);
        return obj;
    }

    public Leader insert(Leader obj) {
        return useCase.insert(obj);
    }

    public void delete(String id) {
        useCase.deleteById(id);
    }

    public Leader update(Leader obj) {
        //  Leader newObj = findById(obj.getId());
        //  updateData(newObj, obj);
        return useCase.update(obj);
    }

    private void updateData(Leader newObj, Leader obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }

    public Leader fromDTO( LeaderDTO objDto) {
        return new Leader(objDto.getId(), objDto.getName(), objDto.getEmail(), objDto.getPhone());
    }
}