package com.fabianospdev.volunteer.usecases.secretary;

import com.fabianospdev.volunteer.dto.SecretaryDTO;
import com.fabianospdev.volunteer.models.SecretaryModel;
import com.fabianospdev.volunteer.repositories.SecretaryRepository;
import com.fabianospdev.volunteer.services.exception.ObjectNotExistsException;
import com.fabianospdev.volunteer.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SecretaryUseCase{

    private final SecretaryRepository secretaryRepository;
    private final MessageSource messageSource;

    @Autowired(required = true)
    public SecretaryUseCase( SecretaryRepository secretaryRepository, MessageSource messageSource ) {
        this.secretaryRepository = secretaryRepository;
        this.messageSource = messageSource;
    }

    public List<SecretaryDTO> findAllDTO() {
        return secretaryRepository.findAllDTO();
    }


    public List<SecretaryModel> findAll() {
        return secretaryRepository.findAll();
    }

    public SecretaryModel findById(String id ) {
        Optional<SecretaryModel> obj = secretaryRepository.findById( id );
        return obj.orElseThrow( () -> new ObjectNotFoundException( "Object not found" ) );
    }

    public SecretaryModel insert(SecretaryModel secretary ) {
        return secretaryRepository.insert( secretary );
    }

    public SecretaryModel update(SecretaryModel secretary ) {
        Optional<SecretaryModel> obj = secretaryRepository.findById( secretary.getId() );

        if ( obj.isEmpty() ) {
            String message = messageSource.getMessage( "object.not.exists", new Object[]{secretary.getId()}, LocaleContextHolder.getLocale() );
            throw new ObjectNotExistsException( message );
        }

        return secretaryRepository.save( secretary );
    }

    public void deleteById( String id ) {
        secretaryRepository.deleteById( id );
    }

    private SecretaryDTO convertToSecretaryDTO( SecretaryModel secretary ) {

        if ( secretary == null ) {
            return new SecretaryDTO();
        }

        return new SecretaryDTO( secretary );
    }
}