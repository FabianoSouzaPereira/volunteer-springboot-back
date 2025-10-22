package com.fabianospdev.volunteer.usecases.volunteer;

import com.fabianospdev.volunteer.dto.VolunteerDTO;
import com.fabianospdev.volunteer.models.VolunteerModel;
import com.fabianospdev.volunteer.repositories.VolunteerRepository;
import com.fabianospdev.volunteer.services.exception.ObjectNotExistsException;
import com.fabianospdev.volunteer.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolunteerUseCase{

    private final VolunteerRepository volunteerRepository;
    private final MessageSource messageSource;

    @Autowired(required = true)
    public VolunteerUseCase( VolunteerRepository volunteerRepository, MessageSource messageSource ) {
        this.volunteerRepository = volunteerRepository;
        this.messageSource = messageSource;
    }

    public List<VolunteerDTO> findAllDTO() {
        return volunteerRepository.findAllDTO();
    }

    public List<VolunteerModel> findAll() {
        return volunteerRepository.findAll();
    }

    public VolunteerModel findById(String id ) {
        Optional<VolunteerModel> obj = volunteerRepository.findById( id );
        return obj.orElseThrow( () -> new ObjectNotFoundException( "Object not found" ) );
    }

    public VolunteerModel insert(VolunteerModel volunteer ) {
        return volunteerRepository.insert( volunteer );
    }

    public VolunteerModel update(VolunteerModel volunteer ) {
        Optional<VolunteerModel> obj = volunteerRepository.findById( volunteer.getId() );

        if ( obj.isEmpty() ) {
            String message = messageSource.getMessage( "object.not.exists", new Object[]{volunteer.getId()}, LocaleContextHolder.getLocale() );
            throw new ObjectNotExistsException( message );
        }

        return volunteerRepository.save( volunteer );
    }

    public void deleteById( String id ) {
        volunteerRepository.deleteById( id );
    }

    private VolunteerDTO convertToVolunteerDTO( VolunteerModel volunteer ) {

        if ( volunteer == null ) {
            return new VolunteerDTO();
        }

        return new VolunteerDTO( volunteer );
    }
}