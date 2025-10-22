package com.fabianospdev.volunteer.usecases.partner;

import com.fabianospdev.volunteer.dto.PartnerDTO;
import com.fabianospdev.volunteer.models.PartnerModel;
import com.fabianospdev.volunteer.repositories.PartnerRepository;
import com.fabianospdev.volunteer.services.exception.ObjectNotExistsException;
import com.fabianospdev.volunteer.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartnerUseCase{

    private final PartnerRepository partnerRepository;
    private final MessageSource messageSource;

    @Autowired(required = true)
    public PartnerUseCase( PartnerRepository partnerRepository, MessageSource messageSource ) {
        this.partnerRepository = partnerRepository;
        this.messageSource = messageSource;
    }

    public List<PartnerDTO> findAllDTO() {
        return partnerRepository.findAllDTO();
    }


    public List<PartnerModel> findAll() {
        return partnerRepository.findAll();
    }

    public PartnerModel findById(String id ) {
        Optional<PartnerModel> obj = partnerRepository.findById( id );
        return obj.orElseThrow( () -> new ObjectNotFoundException( "Object not found" ) );
    }

    public PartnerModel insert(PartnerModel partner ) {
        return partnerRepository.insert( partner );
    }

    public PartnerModel update(PartnerModel partner ) {
        Optional<PartnerModel> obj = partnerRepository.findById( partner.getId() );

        if ( obj.isEmpty() ) {
            String message = messageSource.getMessage( "object.not.exists", new Object[]{partner.getId()}, LocaleContextHolder.getLocale() );
            throw new ObjectNotExistsException( message );
        }

        return partnerRepository.save( partner );
    }

    public void deleteById( String id ) {
        partnerRepository.deleteById( id );
    }

    private PartnerDTO convertToPartnerDTO( PartnerModel partner ) {

        if ( partner == null ) {
            return new PartnerDTO();
        }

        return new PartnerDTO( partner );
    }
}