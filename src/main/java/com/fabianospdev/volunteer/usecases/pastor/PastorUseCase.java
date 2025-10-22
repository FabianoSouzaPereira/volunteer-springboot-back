package com.fabianospdev.volunteer.usecases.pastor;

import com.fabianospdev.volunteer.dto.PastorDTO;
import com.fabianospdev.volunteer.models.PastorModel;
import com.fabianospdev.volunteer.repositories.PastorRepository;
import com.fabianospdev.volunteer.services.exception.ObjectNotExistsException;
import com.fabianospdev.volunteer.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PastorUseCase{

    private final PastorRepository pastorRepository;
    private final MessageSource messageSource;

    @Autowired(required = true)
    public PastorUseCase( PastorRepository pastorRepository, MessageSource messageSource ) {
        this.pastorRepository = pastorRepository;
        this.messageSource = messageSource;
    }

    public List<PastorDTO> findAllDTO() {
        return pastorRepository.findAllDTO();
    }

    public List<PastorModel> findAll() {
        return pastorRepository.findAll();
    }

    public PastorModel findById(String id ) {
        Optional<PastorModel> obj = pastorRepository.findById( id );
        return obj.orElseThrow( () -> new ObjectNotFoundException( "Object not found" ) );
    }

    public PastorModel insert(PastorModel pastor ) {
        return pastorRepository.insert( pastor );
    }

    public PastorModel update(PastorModel pastor ) {
        Optional<PastorModel> obj = pastorRepository.findById( pastor.getId() );

        if ( obj.isEmpty() ) {
            String message = messageSource.getMessage( "object.not.exists", new Object[]{pastor.getId()}, LocaleContextHolder.getLocale() );
            throw new ObjectNotExistsException( message );
        }

        return pastorRepository.save( pastor );
    }

    public void deleteById( String id ) {
        pastorRepository.deleteById( id );
    }

    private PastorDTO convertToPastorDTO( PastorModel pastor ) {

        if ( pastor == null ) {
            return new PastorDTO();
        }

        return new PastorDTO( pastor );
    }
}