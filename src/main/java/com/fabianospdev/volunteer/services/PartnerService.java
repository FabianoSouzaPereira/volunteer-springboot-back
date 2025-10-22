package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.dto.PartnerDTO;
import com.fabianospdev.volunteer.models.PartnerModel;
import com.fabianospdev.volunteer.repositories.PartnerRepository;
import com.fabianospdev.volunteer.usecases.partner.PartnerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerService{

    @Autowired(required = true)
    private PartnerRepository repository;

    @Autowired(required = true)
    private PartnerUseCase useCase;


    public List<PartnerModel> findAll() {
        return useCase.findAll();
    }


    public List<PartnerDTO> findAllDTO() {
        return useCase.findAllDTO();
    }

    private PartnerDTO convertToPartnerDTO( PartnerModel partner ) {
        PartnerDTO partnerDTO = new PartnerDTO( partner );
        return partnerDTO;
    }


    public PartnerModel findById(String id ) {
        PartnerModel obj = useCase.findById( id );
        return obj;
    }

    public PartnerModel insert(PartnerModel obj ) {
        return useCase.insert( obj );
    }

    public void delete( String id ) {
        useCase.deleteById( id );
    }

    public PartnerModel update(PartnerModel obj ) {
        return useCase.update( obj );
    }

    private void updateData(PartnerModel newObj, PartnerModel obj ) {
        newObj.setName( obj.getName() );
        newObj.setEmail( obj.getEmail() );
    }

    public PartnerModel fromDTO(PartnerDTO objDto ) {
        return new PartnerModel( objDto.getId(), objDto.getName(), objDto.getEmail(), objDto.getPhone() );
    }
}