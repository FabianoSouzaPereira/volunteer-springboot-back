package com.fabianospdev.volunteer.repositories;

import com.fabianospdev.volunteer.dto.PartnerDTO;
import com.fabianospdev.volunteer.models.Partner;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepositoryExtension {
    @Query("{}")
    List<Partner> findAllList();

    @Query(value="{}", fields="{ 'id' : 1, 'name' : 1, 'phone' : 1, 'email' : 1}")
    List<PartnerDTO> findAllDTO();
}