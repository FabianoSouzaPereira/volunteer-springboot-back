package com.fabianospdev.volunteer.repositories;

import com.fabianospdev.volunteer.dto.VolunteerDTO;
import com.fabianospdev.volunteer.models.VolunteerModel;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerRepositoryExtension {
    @Query("{}")
    List<VolunteerModel> findAllList();

    @Query(value="{}", fields="{ 'id' : 1, 'name' : 1, 'phone' : 1, 'email' : 1}")
    List<VolunteerDTO> findAllDTO();
}