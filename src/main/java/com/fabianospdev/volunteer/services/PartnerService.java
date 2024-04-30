package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.dto.PartnerDTO;
import com.fabianospdev.volunteer.models.Partner;
import com.fabianospdev.volunteer.repositories.PartnerRepository;
import com.fabianospdev.volunteer.usecases.partner.PartnerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerService{
    @Autowired(required=true)
    private PartnerRepository repository;

    @Autowired
    private PartnerUseCase useCase;


    public List<Partner> findAll() {
        return useCase.findAll();
    }

    public Partner findById(String id) {
        Partner obj = useCase.findById(id);
        return obj;
    }

    public Partner insert(Partner obj) {
        return useCase.insert(obj);
    }

    public void delete(String id) {
        useCase.deleteById(id);
    }

    public Partner update(Partner obj) {
        //  Partner newObj = findById(obj.getId());
        //  updateData(newObj, obj);
        return useCase.update(obj);
    }

    private void updateData(Partner newObj, Partner obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }

    public Partner fromDTO( PartnerDTO objDto) {
        return new Partner(objDto.getId(), objDto.getName(), objDto.getEmail(), objDto.getPhone());
    }
}