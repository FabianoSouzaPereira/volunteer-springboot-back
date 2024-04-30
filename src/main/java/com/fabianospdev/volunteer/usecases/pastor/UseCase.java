package com.fabianospdev.volunteer.usecases.pastor;

import com.fabianospdev.volunteer.models.Pastor;
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
public class UseCase{

    private final PastorRepository pastorRepository;
    private final MessageSource messageSource;

    @Autowired(required=true)
    public UseCase( PastorRepository pastorRepository, MessageSource messageSource) {
        this.pastorRepository = pastorRepository;
        this.messageSource = messageSource;
    }

    public List<Pastor> findAll() {
        return pastorRepository.findAll();
    }

    public Pastor findById(String id) {
        Optional<Pastor> obj = pastorRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public Pastor insert(Pastor pastor) {
        return pastorRepository.insert(pastor);
    }

    public Pastor update(Pastor pastor) {
                Optional<Pastor> obj = pastorRepository.findById(pastor.getId());

        if (obj.isEmpty()) {
            String message = messageSource.getMessage("object.not.exists", new Object[]{pastor.getId()}, LocaleContextHolder.getLocale());
            throw new ObjectNotExistsException(message);
        }

        return pastorRepository.save(pastor);
    }

    public void deleteById(String id) {
        pastorRepository.deleteById(id);
    }
}