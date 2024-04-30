package com.fabianospdev.volunteer.usecases.leader;


import com.fabianospdev.volunteer.models.Leader;
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
public class UseCase {

    private final LeaderRepository leaderRepository;
    private final MessageSource messageSource;

    @Autowired(required=true)
    public UseCase( LeaderRepository leaderRepository, MessageSource messageSource) {
        this.leaderRepository = leaderRepository;
        this.messageSource = messageSource;
    }

    public List<Leader> findAll() {
        return leaderRepository.findAll();
    }

    public Leader findById(String id) {
        Optional<Leader> obj = leaderRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public Leader insert(Leader leader) {
        return leaderRepository.insert(leader);
    }

    public Leader update(Leader leader) {
                Optional<Leader> obj = leaderRepository.findById(leader.getId());

        if (obj.isEmpty()) {
            String message = messageSource.getMessage("object.not.exists", new Object[]{leader.getId()}, LocaleContextHolder.getLocale());
            throw new ObjectNotExistsException(message);
        }

        return leaderRepository.save(leader);
    }

    public void deleteById(String id) {
        leaderRepository.deleteById(id);
    }
}