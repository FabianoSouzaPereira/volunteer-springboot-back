package com.fabianospdev.volunteer.usecases.leader;

import com.fabianospdev.volunteer.dto.LeaderDTO;
import com.fabianospdev.volunteer.models.LeaderModel;
import com.fabianospdev.volunteer.repositories.LeaderRepository;
import com.fabianospdev.volunteer.services.exception.ObjectNotExistsException;
import com.fabianospdev.volunteer.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaderUseCase{

    private final LeaderRepository leaderRepository;
    private final MessageSource messageSource;

    @Autowired(required = true)
    public LeaderUseCase( LeaderRepository leaderRepository, MessageSource messageSource ) {
        this.leaderRepository = leaderRepository;
        this.messageSource = messageSource;
    }

    public List<LeaderDTO> findAllDTO() {
        return leaderRepository.findAllDTO();
    }


    public List<LeaderModel> findAll() {
        return leaderRepository.findAll();
    }

    public LeaderModel findById(String id ) {
        Optional<LeaderModel> obj = leaderRepository.findById( id );
        return obj.orElseThrow( () -> new ObjectNotFoundException( "Object not found" ) );
    }

    public LeaderModel insert(LeaderModel leader ) {
        return leaderRepository.insert( leader );
    }

    public LeaderModel update(LeaderModel leader ) {
        Optional<LeaderModel> obj = leaderRepository.findById( leader.getId() );

        if ( obj.isEmpty() ) {
            String message = messageSource.getMessage( "object.not.exists", new Object[]{leader.getId()}, LocaleContextHolder.getLocale() );
            throw new ObjectNotExistsException( message );
        }

        return leaderRepository.save( leader );
    }

    public void deleteById( String id ) {
        leaderRepository.deleteById( id );
    }

    private LeaderDTO convertToLeaderDTO( LeaderModel leader ) {

        if ( leader == null ) {
            return new LeaderDTO();
        }

        return new LeaderDTO( leader );
    }
}