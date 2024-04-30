package com.fabianospdev.volunteer.usecases.partner;

import com.fabianospdev.volunteer.models.Partner;
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

    @Autowired(required=true)
    public PartnerUseCase( PartnerRepository partnerRepository, MessageSource messageSource) {
        this.partnerRepository = partnerRepository;
        this.messageSource = messageSource;
    }

    public List<Partner> findAll() {
        return partnerRepository.findAll();
    }

    public Partner findById(String id) {
        Optional<Partner> obj = partnerRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public Partner insert(Partner partner) {
        return partnerRepository.insert(partner);
    }

    public Partner update(Partner partner) {
                Optional<Partner> obj = partnerRepository.findById(partner.getId());

        if (obj.isEmpty()) {
            String message = messageSource.getMessage("object.not.exists", new Object[]{partner.getId()}, LocaleContextHolder.getLocale());
            throw new ObjectNotExistsException(message);
        }

        return partnerRepository.save(partner);
    }

    public void deleteById(String id) {
        partnerRepository.deleteById(id);
    }
}